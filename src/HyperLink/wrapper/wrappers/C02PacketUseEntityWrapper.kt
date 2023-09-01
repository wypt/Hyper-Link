package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.wrappers.C02PacketUseEntityWrapper

@WrapperClass("net.minecraft.network.play.client.C02PacketUseEntity")
class C02PacketUseEntityWrapper : PacketWrapper {
    constructor(`object`: Any?) : super(`object`)
    constructor(entity: EntityWrapper, action: Any?) : super(createObject(allocateConstructor(C02PacketUseEntityWrapper::class.java, EntityWrapper::class.java, C02PacketUseEntityActionWrapper::class.java), entity.getObject(), action))
}
