package HyperLink.injection.impl

import HyperLink.HyperLink
import HyperLink.annotations.Inject
import HyperLink.events.Render3DEvent
import HyperLink.injection.AbstractHook
import HyperLink.util.ASMUtil
import com.darkmagician6.eventapi.EventManager
import org.lwjgl.opengl.GL11
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

@Inject("net/minecraft/client/renderer/EntityRenderer")
@Suppress("unused")
class EntityRendererHook : AbstractHook() {
    override fun hook(cn: ClassNode) {
        val renderWorldPass = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/renderer/EntityRenderer/renderWorldPass"), "(IFJ)V")
        renderWorldPass.instructions.insertBefore(renderWorldPass.instructions.last.previous, MethodInsnNode(Opcodes.INVOKESTATIC, EntityRendererHook::class.java.getName().replace(".", "/"), "renderWorldPassCall", "()V", false))
    }

    companion object {
        @JvmStatic
        fun renderWorldPassCall() {
            GL11.glPushMatrix()
            EventManager.call(Render3DEvent())
            GL11.glPopMatrix()
        }
    }
}
