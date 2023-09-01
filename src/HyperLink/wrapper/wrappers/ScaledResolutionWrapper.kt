package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper
import HyperLink.wrapper.wrappers.ScaledResolutionWrapper

@WrapperClass("net.minecraft.client.gui.ScaledResolution")
class ScaledResolutionWrapper : Wrapper {
    constructor(`object`: Any?) : super(`object`)
    constructor(wrapper: MinecraftWrapper) : super(createObject(ScaledResolutionWrapper::class.java, wrapper.getObject()))

    val scaledWidth: Int
        get() = getFieldValue("scaledWidth") as Int
    val scaledHeight: Int
        get() = getFieldValue("scaledHeight") as Int
}
