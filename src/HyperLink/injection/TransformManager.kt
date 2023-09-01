package HyperLink.injection

import HyperLink.HyperLink
import HyperLink.annotations.Inject
import HyperLink.injection.impl.*
import HyperLink.util.FileUtil.write
import HyperLink.util.LogUtil.log
import javassist.ClassPool
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.io.IOException
import java.lang.instrument.ClassDefinition
import java.lang.instrument.Instrumentation
import java.util.function.Consumer

class TransformManager {
    protected val values: MutableList<AbstractHook?> = ArrayList()
    private val oldHooksBytes: MutableMap<Class<*>?, ByteArray> = HashMap()

    init {
        registerHook(GuiIngameHook::class.java, KeyBindingHook::class.java, MinecraftHook::class.java, EntityPlayerSPHook::class.java, EntityRendererHook::class.java, NetworkManagerHook::class.java)
    }

    fun getValues(): List<AbstractHook?> {
        return values
    }

    @SafeVarargs
    protected fun registerHook(vararg classes: Class<out AbstractHook?>) {
        for (aClass in classes) {
            try {
                values.add(aClass.getDeclaredConstructor().newInstance())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun getValue(clazz: Class<*>): AbstractHook? {
        return values
                .stream()
                .filter { o: AbstractHook? -> o!!.javaClass == clazz }
                .findFirst()
                .orElse(null)
    }

    fun hook() {
        val nativeLoaded: Boolean
        nativeLoaded = try {
            HyperLink.getClassByte(MutableMap::class.java)
            true
        } catch (e: UnsatisfiedLinkError) {
            false
        }
        values.forEach(Consumer<AbstractHook?> { hook: AbstractHook? ->
            try {
                val info = hook!!.javaClass.getAnnotation(Inject::class.java)
                        ?: throw RuntimeException(hook.javaClass.getName() + " is not annotated with @Inject")
                var classToHook: Class<*>? = null
                classToHook = Class.forName(HyperLink.getInstance().mapping.getClass(info.value).replace("/", "."))
                val classBytes = if (nativeLoaded) HyperLink.getClassByte(classToHook) else ClassPool.getDefault()[classToHook.getName()].toBytecode()
                log("Starting hook: " + classToHook.getName() + " (" + classBytes.size + ")")
                oldHooksBytes[classToHook] = classBytes
                val cr = ClassReader(classBytes)
                val cn = ClassNode()
                cr.accept(cn, 0)
                hook.hook(cn)
                val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
                cn.accept(cw)
                val hookedClassBytes = cw.toByteArray()
                val classLocation = File(cn.name.replace("/", "_") + ".class")
                try {
                    write(classLocation, hookedClassBytes)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val err = if (nativeLoaded) HyperLink.setClassByte(classToHook, hookedClassBytes) else 0
                if (!nativeLoaded) {
                    val instrumentation = Class.forName("sun.tools.attach.WindowsVirtualMachine").getMethod("work").invoke(null) as Instrumentation
                    instrumentation.redefineClasses(ClassDefinition(classToHook, hookedClassBytes))
                }
                if (err != 0) {
                    throw RuntimeException("Hook " + hook.javaClass.getName() + " returned a jvmti error while redefining class: " + err)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    fun unhook() {
        oldHooksBytes.forEach { (clazz: Class<*>?, bytes: ByteArray?) -> HyperLink.setClassByte(clazz, bytes) }
    }
}
