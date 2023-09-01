package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import java.awt.image.BufferedImage

@WrapperClass("net.minecraft.client.renderer.texture.DynamicTexture")
class DynamicTextureWrapper(bufferedImage: BufferedImage?) : AbstractTextureWrapper(createObject(DynamicTextureWrapper::class.java, bufferedImage))
