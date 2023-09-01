package HyperLink.injection.impl

import HyperLink.HyperLink
import HyperLink.annotations.Inject
import HyperLink.events.KeyEvent
import HyperLink.injection.AbstractHook
import HyperLink.util.ASMUtil
import com.darkmagician6.eventapi.EventManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.VarInsnNode

@Inject("net/minecraft/client/settings/KeyBinding")
@Suppress("unused")
class KeyBindingHook : AbstractHook() {
    override fun hook(cn: ClassNode) {
        val onTickMn = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/settings/KeyBinding/onTick"), "(I)V")
        val list = InsnList()
        list.add(VarInsnNode(Opcodes.ILOAD, 0))
        list.add(MethodInsnNode(Opcodes.INVOKESTATIC, this.javaClass.getName().replace(".", "/"), "onKeyCall", "(I)V"))
        onTickMn.instructions.insertBefore(onTickMn.instructions[1], list)
    }

    companion object {
        @JvmStatic
        fun onKeyCall(key: Int) {
            EventManager.call(KeyEvent(key))
        }
    }
}
