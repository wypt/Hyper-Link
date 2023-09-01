package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.client.renderer.vertex.DefaultVertexFormats")
class DefaultVertexFormatsWrapper(`object`: Any?) : Wrapper(`object`) {
    companion object {
        fun POSITION_COLOR(): Any {
            return getStatic(DefaultVertexFormatsWrapper::class.java, "POSITION_COLOR")
        }

        @JvmStatic
        fun POSITION(): Any {
            return getStatic(DefaultVertexFormatsWrapper::class.java, "POSITION")
        }
    }
}
