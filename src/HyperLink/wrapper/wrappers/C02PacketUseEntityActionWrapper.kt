package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.network.play.client.C02PacketUseEntity\$Action")
class C02PacketUseEntityActionWrapper(`object`: Any?) : Wrapper(`object`) {
    companion object {
        fun ATTACK(): Any {
            return getStatic(C02PacketUseEntityActionWrapper::class.java, "ATTACK")
        }
    }
}
