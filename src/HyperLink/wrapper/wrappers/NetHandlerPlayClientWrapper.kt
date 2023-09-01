package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.client.network.NetHandlerPlayClient")
class NetHandlerPlayClientWrapper(`object`: Any?) : Wrapper(`object`) {
    fun addToSendQueue(p_147297_1_: PacketWrapper) {
        invoke("addToSendQueue", p_147297_1_.getObject())
    }
}
