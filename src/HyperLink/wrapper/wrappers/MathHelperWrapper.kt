package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.util.MathHelper")
class MathHelperWrapper(`object`: Any?) : Wrapper(`object`) {
    companion object {
        fun atan2(p_181159_0_: Double, p_181159_2_: Double): Double {
            return invokeStatic(MathHelperWrapper::class.java, "atan2", p_181159_0_, p_181159_2_) as Double
        }
    }
}
