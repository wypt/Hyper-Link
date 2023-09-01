package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.util.AxisAlignedBB")
class AxisAlignedBBWrapper(`object`: Any?) : Wrapper(`object`) {
    fun minX(): Double {
        return getFieldValue("minX") as Double
    }

    fun minY(): Double {
        return getFieldValue("minY") as Double
    }

    fun maxY(): Double {
        return getFieldValue("maxY") as Double
    }
}
