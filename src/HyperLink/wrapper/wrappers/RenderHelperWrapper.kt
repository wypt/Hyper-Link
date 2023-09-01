package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.client.renderer.RenderHelper")
class RenderHelperWrapper(`object`: Any?) : Wrapper(`object`) {
    companion object {
        @JvmStatic
        fun disableStandardItemLighting() {
            invokeStatic(RenderHelperWrapper::class.java, "disableStandardItemLighting")
        }

        @JvmStatic
        fun enableGUIStandardItemLighting() {
            invokeStatic(RenderHelperWrapper::class.java, "enableGUIStandardItemLighting")
        }
    }
}
