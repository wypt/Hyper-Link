package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass

@WrapperClass("net.minecraft.client.renderer.texture.AbstractTexture")
open class AbstractTextureWrapper(`object`: Any?) : ITextureObjectWrapper(`object`) {
    val glTextureId: Int
        get() = getFieldValue(AbstractTextureWrapper::class.java, "glTextureId") as Int
}
