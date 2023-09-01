package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper
import org.lwjgl.opengl.GL11

@WrapperClass("net.minecraft.client.renderer.GlStateManager")
class GlStateManagerWrapper(`object`: Any?) : Wrapper(`object`) {
    companion object {
        @JvmStatic
        fun clear(mask: Int) {
            invokeStatic(GlStateManagerWrapper::class.java, "clear", mask)
        }

        @JvmStatic
        fun enableDepth() {
            invokeStatic(GlStateManagerWrapper::class.java, "enableDepth")
        }

        @JvmStatic
        fun resetColor() {
            invokeStatic(GlStateManagerWrapper::class.java, "resetColor")
        }

        @JvmStatic
        fun enableLighting() {
            invokeStatic(GlStateManagerWrapper::class.java, "enableLighting")
        }

        @JvmStatic
        fun scale(x: Double, y: Double, z: Double) {
            GL11.glScaled(x, y, z)
        }

        @JvmStatic
        fun disableLighting() {
            invokeStatic(GlStateManagerWrapper::class.java, "disableLighting")
        }

        @JvmStatic
        fun disableDepth() {
            invokeStatic(GlStateManagerWrapper::class.java, "disableDepth")
        }

        @JvmStatic
        fun enableBlend() {
            invokeStatic(GlStateManagerWrapper::class.java, "enableBlend")
        }

        @JvmStatic
        fun blendFunc(srcFactor: Int, dstFactor: Int) {
            invokeStatic(GlStateManagerWrapper::class.java, "blendFunc", srcFactor, dstFactor)
        }

        @JvmStatic
        fun color(colorRed: Float, colorGreen: Float, colorBlue: Float, colorAlpha: Float) {
            invokeStatic(GlStateManagerWrapper::class.java, "color", colorRed, colorGreen, colorBlue, colorAlpha)
        }

        @JvmStatic
        fun enableAlpha() {
            invokeStatic(GlStateManagerWrapper::class.java, "enableAlpha")
        }

        @JvmStatic
        fun disableBlend() {
            invokeStatic(GlStateManagerWrapper::class.java, "disableBlend")
        }

        @JvmStatic
        fun enableTexture2D() {
            invokeStatic(GlStateManagerWrapper::class.java, "enableTexture2D")
        }

        @JvmStatic
        fun bindTexture(texture: Int) {
            invokeStatic(GlStateManagerWrapper::class.java, "bindTexture", texture)
        }

        @JvmStatic
        fun disableTexture2D() {
            invokeStatic(GlStateManagerWrapper::class.java, "disableTexture2D")
        }

        @JvmStatic
        fun disableAlpha() {
            invokeStatic(GlStateManagerWrapper::class.java, "disableAlpha")
        }

        fun shadeModel(mode: Int) {
            invokeStatic(GlStateManagerWrapper::class.java, "shadeModel", mode)
        }

        fun tryBlendFuncSeparate(srcFactor: Int, dstFactor: Int, srcFactorAlpha: Int, dstFactorAlpha: Int) {
            invokeStatic(GlStateManagerWrapper::class.java, "tryBlendFuncSeparate", srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha)
        }
    }
}
