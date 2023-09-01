package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.client.renderer.WorldRenderer")
class WorldRendererWrapper(`object`: Any?) : Wrapper(`object`) {
    fun begin(glMode: Int, format: Any?) {
        invoke(WorldRendererWrapper::class.java, "begin", glMode, format)
    }

    fun pos(x: Double, y: Double, z: Double): WorldRendererWrapper {
        return WorldRendererWrapper(invoke(WorldRendererWrapper::class.java, "pos", x, y, z))
    }

    fun color(red: Float, green: Float, blue: Float, alpha: Float): WorldRendererWrapper {
        return WorldRendererWrapper(invoke(WorldRendererWrapper::class.java, "color", red, green, blue, alpha))
    }

    fun endVertex() {
        invoke(WorldRendererWrapper::class.java, "endVertex")
    }
}
