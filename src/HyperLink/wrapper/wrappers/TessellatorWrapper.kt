package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.client.renderer.Tessellator")
class TessellatorWrapper(`object`: Any?) : Wrapper(`object`) {
    val worldRenderer: WorldRendererWrapper
        get() = WorldRendererWrapper(invoke(TessellatorWrapper::class.java, "getWorldRenderer"))

    fun draw() {
        invoke(TessellatorWrapper::class.java, "draw")
    }

    companion object {
        @JvmStatic
        val instance: TessellatorWrapper
            get() = TessellatorWrapper(invokeStatic(TessellatorWrapper::class.java, "getInstance"))
    }
}
