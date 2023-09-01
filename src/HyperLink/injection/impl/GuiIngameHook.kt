package HyperLink.injection.impl

import HyperLink.HyperLink
import HyperLink.annotations.Inject
import HyperLink.events.Render2DEvent
import HyperLink.injection.AbstractHook
import HyperLink.util.RenderUtil
import HyperLink.util.shader.BlurUtil
import HyperLink.wrapper.wrappers.ScaledResolutionWrapper
import com.darkmagician6.eventapi.EventManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.VarInsnNode

@Inject("net/minecraft/client/gui/GuiIngame")
@Suppress("unused")
class GuiIngameHook : AbstractHook() {
    override fun hook(cn: ClassNode) {
        for (method in cn.methods) {
            if (method.name == HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/GuiIngame/renderTooltip") && method.desc.contains(HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/gui/ScaledResolution"))) {
                val list = InsnList()
                list.add(VarInsnNode(Opcodes.ALOAD, 1))
                list.add(MethodInsnNode(Opcodes.INVOKESTATIC, GuiIngameHook::class.java.getName().replace(".", "/"), "renderTooltipCall", "(L" + Any::class.java.getName().replace(".", "/") + ";)V", false))
                method.instructions.insertBefore(method.instructions.last.previous, list)
            }
        }
    }

    companion object {
        @JvmStatic
        fun renderTooltipCall(o: Any?) {
            RenderUtil.reset()
            BlurUtil.onRenderGameOverlay(HyperLink.getInstance().instanceManager.minecraft.framebuffer, ScaledResolutionWrapper(o))
            EventManager.call(Render2DEvent(ScaledResolutionWrapper(o)))
        }
    }
}
