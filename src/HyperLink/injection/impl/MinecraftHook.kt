package HyperLink.injection.impl

import HyperLink.HyperLink
import HyperLink.annotations.Inject
import HyperLink.events.TickEvent
import HyperLink.injection.AbstractHook
import HyperLink.util.ASMUtil
import HyperLink.util.RenderUtil
import HyperLink.util.shader.BlurUtil
import com.darkmagician6.eventapi.EventManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

@Inject("net/minecraft/client/Minecraft")
@Suppress("unused")
class MinecraftHook : AbstractHook() {
    override fun hook(cn: ClassNode) {
        val runTick = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/Minecraft/runTick"), "()V")
        val runGameLoop = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/Minecraft/runGameLoop"), "()V")
        val updateFramebufferSize = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/Minecraft/updateFramebufferSize"), "()V")
        runTick.instructions.insertBefore(runTick.instructions.first, MethodInsnNode(Opcodes.INVOKESTATIC, MinecraftHook::class.java.getName().replace(".", "/"), "runTickCall", "()V", false))
        runGameLoop.instructions.insertBefore(runGameLoop.instructions.first, MethodInsnNode(Opcodes.INVOKESTATIC, MinecraftHook::class.java.getName().replace(".", "/"), "runGameLoopCall", "()V", false))
        updateFramebufferSize.instructions.insertBefore(updateFramebufferSize.instructions.last.previous, MethodInsnNode(Opcodes.INVOKESTATIC, MinecraftHook::class.java.getName().replace(".", "/"), "updateFramebufferSizeCall", "()V"))
    }

    companion object {
        @JvmStatic
        fun runTickCall() {
            EventManager.call(TickEvent())
        }

        @JvmStatic
        fun runGameLoopCall() {
            RenderUtil.updateDelta()
        }

        @JvmStatic
        fun updateFramebufferSizeCall() {
            BlurUtil.onFrameBufferResize(HyperLink.getInstance().instanceManager.minecraft.displayWidth(), HyperLink.getInstance().instanceManager.minecraft.displayHeight())
        }
    }
}
