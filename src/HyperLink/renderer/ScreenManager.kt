package HyperLink.renderer

import HyperLink.HyperLink
import HyperLink.renderer.AbstractScreen
import HyperLink.util.FileUtil.write
import HyperLink.wrapper.Wrapper
import HyperLink.wrapper.wrappers.GuiScreenWrapper
import org.objectweb.asm.*
import sun.misc.Unsafe
import java.io.File
import java.lang.reflect.Field

class ScreenManager : Opcodes {
    private var index = 0
    private var unsafe: Unsafe? = null
    private var nativeLoaded = false

    init {
        try {
            HyperLink.getClassByte(Math::class.java)
            nativeLoaded = true
        } catch (error: UnsatisfiedLinkError) {
            nativeLoaded = false
            try {
                val unsafe: Field
                unsafe = Unsafe::class.java.getDeclaredField("theUnsafe")
                unsafe.setAccessible(true)
                this.unsafe = unsafe[null] as Unsafe
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun makeScreen(screen: AbstractScreen?): Any? {
        try {
            val dump = makeScreenByte("Screen$index")
            write(File("Screen$index.class"), dump)
            val screenClass = if (nativeLoaded) HyperLink.loadClassByte(dump) else unsafe!!.defineClass("HyperLink.renderer.Screen$index", dump, 0, dump.size, Wrapper.allocateClass(GuiScreenWrapper::class.java).getClassLoader(), null)
            index++
            return screenClass.getConstructor(AbstractScreen::class.java).newInstance(screen)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return null
    }

    fun makeScreenByte(name: String): ByteArray {
        val classWriter = ClassWriter(0)
        var fieldVisitor: FieldVisitor
        var methodVisitor: MethodVisitor
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC or Opcodes.ACC_SUPER, "HyperLink/renderer/$name", null, "net/minecraft/client/gui/GuiScreen", null)
        classWriter.visitSource("Screen.java", null)
        run {
            fieldVisitor = classWriter.visitField(Opcodes.ACC_PRIVATE or Opcodes.ACC_FINAL, "screen", "LHyperLink/renderer/AbstractScreen;", null, null)
            fieldVisitor.visitEnd()
        }
        run {
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(LHyperLink/renderer/AbstractScreen;)V", null, null)
            methodVisitor.visitCode()
            val label0 = Label()
            methodVisitor.visitLabel(label0)
            methodVisitor.visitLineNumber(11, label0)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "net/minecraft/client/gui/GuiScreen", "<init>", "()V", false)
            val label1 = Label()
            methodVisitor.visitLabel(label1)
            methodVisitor.visitLineNumber(12, label1)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 1)
            methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, "HyperLink/renderer/$name", "screen", "LHyperLink/renderer/AbstractScreen;")
            val label2 = Label()
            methodVisitor.visitLabel(label2)
            methodVisitor.visitLineNumber(13, label2)
            methodVisitor.visitInsn(Opcodes.RETURN)
            val label3 = Label()
            methodVisitor.visitLabel(label3)
            methodVisitor.visitLocalVariable("this", "LHyperLink/renderer/$name;", null, label0, label3, 0)
            methodVisitor.visitLocalVariable("screen", "LHyperLink/renderer/AbstractScreen;", null, label0, label3, 1)
            methodVisitor.visitMaxs(2, 2)
            methodVisitor.visitEnd()
        }
        run {
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiScreen/drawScreen"), "(IIF)V", null, null)
            methodVisitor.visitCode()
            val label0 = Label()
            methodVisitor.visitLabel(label0)
            methodVisitor.visitLineNumber(17, label0)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "HyperLink/renderer/$name", "screen", "LHyperLink/renderer/AbstractScreen;")
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2)
            methodVisitor.visitVarInsn(Opcodes.FLOAD, 3)
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "HyperLink/renderer/AbstractScreen", "drawScreen", "(IIF)V", false)
            val label1 = Label()
            methodVisitor.visitLabel(label1)
            methodVisitor.visitLineNumber(18, label1)
            methodVisitor.visitInsn(Opcodes.RETURN)
            val label2 = Label()
            methodVisitor.visitLabel(label2)
            methodVisitor.visitLocalVariable("this", "LHyperLink/renderer/$name;", null, label0, label2, 0)
            methodVisitor.visitLocalVariable("mouseX", "I", null, label0, label2, 1)
            methodVisitor.visitLocalVariable("mouseY", "I", null, label0, label2, 2)
            methodVisitor.visitLocalVariable("partialTicks", "F", null, label0, label2, 3)
            methodVisitor.visitMaxs(4, 4)
            methodVisitor.visitEnd()
        }
        run {
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PROTECTED, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiScreen/mouseClicked"), "(III)V", null, arrayOf("java/io/IOException"))
            methodVisitor.visitCode()
            val label0 = Label()
            methodVisitor.visitLabel(label0)
            methodVisitor.visitLineNumber(22, label0)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "HyperLink/renderer/$name", "screen", "LHyperLink/renderer/AbstractScreen;")
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 3)
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "HyperLink/renderer/AbstractScreen", "mouseClicked", "(III)V", false)
            val label1 = Label()
            methodVisitor.visitLabel(label1)
            methodVisitor.visitLineNumber(23, label1)
            methodVisitor.visitInsn(Opcodes.RETURN)
            val label2 = Label()
            methodVisitor.visitLabel(label2)
            methodVisitor.visitLocalVariable("this", "LHyperLink/renderer/$name;", null, label0, label2, 0)
            methodVisitor.visitLocalVariable("mouseX", "I", null, label0, label2, 1)
            methodVisitor.visitLocalVariable("mouseY", "I", null, label0, label2, 2)
            methodVisitor.visitLocalVariable("mouseButton", "I", null, label0, label2, 3)
            methodVisitor.visitMaxs(4, 4)
            methodVisitor.visitEnd()
        }
        run {
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PROTECTED, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiScreen/mouseReleased"), "(III)V", null, null)
            methodVisitor.visitCode()
            val label0 = Label()
            methodVisitor.visitLabel(label0)
            methodVisitor.visitLineNumber(27, label0)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "HyperLink/renderer/$name", "screen", "LHyperLink/renderer/AbstractScreen;")
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 3)
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "HyperLink/renderer/AbstractScreen", "mouseReleased", "(III)V", false)
            val label1 = Label()
            methodVisitor.visitLabel(label1)
            methodVisitor.visitLineNumber(28, label1)
            methodVisitor.visitInsn(Opcodes.RETURN)
            val label2 = Label()
            methodVisitor.visitLabel(label2)
            methodVisitor.visitLocalVariable("this", "LHyperLink/renderer/$name;", null, label0, label2, 0)
            methodVisitor.visitLocalVariable("mouseX", "I", null, label0, label2, 1)
            methodVisitor.visitLocalVariable("mouseY", "I", null, label0, label2, 2)
            methodVisitor.visitLocalVariable("state", "I", null, label0, label2, 3)
            methodVisitor.visitMaxs(4, 4)
            methodVisitor.visitEnd()
        }
        run {
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PROTECTED, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiScreen/mouseClickMove"), "(IIIJ)V", null, null)
            methodVisitor.visitCode()
            val label0 = Label()
            methodVisitor.visitLabel(label0)
            methodVisitor.visitLineNumber(32, label0)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "HyperLink/renderer/$name", "screen", "LHyperLink/renderer/AbstractScreen;")
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 3)
            methodVisitor.visitVarInsn(Opcodes.LLOAD, 4)
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "HyperLink/renderer/AbstractScreen", "mouseClickMove", "(IIIJ)V", false)
            val label1 = Label()
            methodVisitor.visitLabel(label1)
            methodVisitor.visitLineNumber(33, label1)
            methodVisitor.visitInsn(Opcodes.RETURN)
            val label2 = Label()
            methodVisitor.visitLabel(label2)
            methodVisitor.visitLocalVariable("this", "LHyperLink/renderer/$name;", null, label0, label2, 0)
            methodVisitor.visitLocalVariable("mouseX", "I", null, label0, label2, 1)
            methodVisitor.visitLocalVariable("mouseY", "I", null, label0, label2, 2)
            methodVisitor.visitLocalVariable("clickedMouseButton", "I", null, label0, label2, 3)
            methodVisitor.visitLocalVariable("timeSinceLastClick", "J", null, label0, label2, 4)
            methodVisitor.visitMaxs(6, 6)
            methodVisitor.visitEnd()
        }
        run {
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiScreen/handleMouseInput"), "()V", null, arrayOf("java/io/IOException"))
            methodVisitor.visitCode()
            val label0 = Label()
            methodVisitor.visitLabel(label0)
            methodVisitor.visitLineNumber(35, label0)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "net/minecraft/client/gui/GuiScreen", HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiScreen/handleMouseInput"), "()V", false)
            val label1 = Label()
            methodVisitor.visitLabel(label1)
            methodVisitor.visitLineNumber(36, label1)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "HyperLink/renderer/Screen0", "screen", "LHyperLink/renderer/AbstractScreen;")
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "HyperLink/renderer/AbstractScreen", "handleMouseInput", "()V", false)
            val label2 = Label()
            methodVisitor.visitLabel(label2)
            methodVisitor.visitLineNumber(37, label2)
            methodVisitor.visitInsn(Opcodes.RETURN)
            val label3 = Label()
            methodVisitor.visitLabel(label3)
            methodVisitor.visitLocalVariable("this", "LHyperLink/renderer/Screen0;", null, label0, label3, 0)
            methodVisitor.visitMaxs(1, 1)
            methodVisitor.visitEnd()
        }
        run {
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PROTECTED, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiScreen/keyTyped"), "(CI)V", null, arrayOf("java/io/IOException"))
            methodVisitor.visitCode()
            val label0 = Label()
            methodVisitor.visitLabel(label0)
            methodVisitor.visitLineNumber(42, label0)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "HyperLink/renderer/$name", "screen", "LHyperLink/renderer/AbstractScreen;")
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1)
            methodVisitor.visitVarInsn(Opcodes.ILOAD, 2)
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "HyperLink/renderer/AbstractScreen", "keyTyped", "(CI)V", false)
            val label1 = Label()
            methodVisitor.visitLabel(label1)
            methodVisitor.visitLineNumber(43, label1)
            methodVisitor.visitInsn(Opcodes.RETURN)
            val label2 = Label()
            methodVisitor.visitLabel(label2)
            methodVisitor.visitLocalVariable("this", "LHyperLink/renderer/$name;", null, label0, label2, 0)
            methodVisitor.visitLocalVariable("typedChar", "C", null, label0, label2, 1)
            methodVisitor.visitLocalVariable("keyCode", "I", null, label0, label2, 2)
            methodVisitor.visitMaxs(3, 3)
            methodVisitor.visitEnd()
        }
        run {
            methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiScreen/doesGuiPauseGame"), "()Z", null, null)
            methodVisitor.visitCode()
            val label0 = Label()
            methodVisitor.visitLabel(label0)
            methodVisitor.visitLineNumber(36, label0)
            methodVisitor.visitInsn(Opcodes.ICONST_0)
            methodVisitor.visitInsn(Opcodes.IRETURN)
            val label1 = Label()
            methodVisitor.visitLabel(label1)
            methodVisitor.visitLocalVariable("this", "LHyperLink/renderer/Screen0;", null, label0, label1, 0)
            methodVisitor.visitMaxs(1, 1)
            methodVisitor.visitEnd()
        }
        classWriter.visitEnd()
        return classWriter.toByteArray()
    }
}
