package HyperLink.injection.impl

import HyperLink.HyperLink
import HyperLink.annotations.Inject
import HyperLink.events.PacketEvent
import HyperLink.injection.AbstractHook
import HyperLink.util.ASMUtil
import HyperLink.wrapper.Wrapper
import HyperLink.wrapper.wrappers.PacketWrapper
import com.darkmagician6.eventapi.EventManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

@Inject("net/minecraft/network/NetworkManager")
@Suppress("unused")
class NetworkManagerHook : AbstractHook() {
    override fun hook(cn: ClassNode) {
        val channelRead0 = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/network/NetworkManager/channelRead0"), "(Lio/netty/channel/ChannelHandlerContext;L" + Wrapper.allocateClass(PacketWrapper::class.java).getName().replace(".", "/") + ";)V")
        val sendPacket1 = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/network/NetworkManager/sendPacket"), "(L" + Wrapper.allocateClass(PacketWrapper::class.java).getName().replace(".", "/") + ";)V")
        var list = InsnList()
        val startNode = LabelNode()
        list.add(VarInsnNode(Opcodes.ALOAD, 2))
        list.add(MethodInsnNode(Opcodes.INVOKESTATIC, this.javaClass.getName().replace(".", "/"), "channelRead0Call", "(Ljava/lang/Object;)Z"))
        list.add(JumpInsnNode(Opcodes.IFEQ, startNode))
        list.add(InsnNode(Opcodes.RETURN))
        list.add(startNode)
        list.add(FrameNode(Opcodes.F_SAME, 0, null, 0, null))
        channelRead0.instructions.insert(list)
        channelRead0.maxLocals++
        list = InsnList()
        val L0 = LabelNode()
        list.add(VarInsnNode(Opcodes.ALOAD, 1))
        list.add(MethodInsnNode(Opcodes.INVOKESTATIC, this.javaClass.getName().replace(".", "/"), "sendPacketCall", "(Ljava/lang/Object;)Z"))
        list.add(JumpInsnNode(Opcodes.IFEQ, L0))
        list.add(InsnNode(Opcodes.RETURN))
        list.add(L0)
        list.add(FrameNode(Opcodes.F_SAME, 0, null, 0, null))
        sendPacket1.instructions.insert(list)
        sendPacket1.maxLocals++
    }

    companion object {
        @JvmStatic
        fun channelRead0Call(o: Any?): Boolean {
            val event = PacketEvent(PacketEvent.Side.IN, PacketWrapper(o))
            EventManager.call(event)
            return event.isCancelled
        }

        @JvmStatic
        fun sendPacketCall(o: Any?): Boolean {
            val event = PacketEvent(PacketEvent.Side.OUT, PacketWrapper(o))
            EventManager.call(event)
            return event.isCancelled
        }
    }
}
