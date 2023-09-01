package HyperLink.util

import HyperLink.wrapper.wrappers.DefaultVertexFormatsWrapper.Companion.POSITION
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.blendFunc
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.clear
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.disableAlpha
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.disableBlend
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.disableDepth
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.disableLighting
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.disableTexture2D
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.enableAlpha
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.enableBlend
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.enableDepth
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.enableLighting
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.enableTexture2D
import HyperLink.wrapper.wrappers.GlStateManagerWrapper.Companion.resetColor
import HyperLink.wrapper.wrappers.RenderHelperWrapper.Companion.disableStandardItemLighting
import HyperLink.wrapper.wrappers.RenderHelperWrapper.Companion.enableGUIStandardItemLighting
import HyperLink.wrapper.wrappers.TessellatorWrapper.Companion.instance
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import kotlin.math.max
import kotlin.math.min

object RenderUtil {
    var delta = 0f
    private var lastFrame: Long = 0
    fun getAnimationState(animation: Float, finalState: Float, speed: Float): Float {
        var animation = animation
        val add = delta * speed
        animation = if (animation < finalState) min((animation + add).toDouble(), finalState.toDouble()).toFloat() else max((animation - add).toDouble(), finalState.toDouble()).toFloat()
        return animation
    }

    fun reset() {
        GL11.glPushMatrix()
        enableGUIStandardItemLighting()
        disableAlpha()
        clear(256)
        disableLighting()
        disableDepth()
        disableBlend()
        enableLighting()
        enableDepth()
        disableLighting()
        disableDepth()
        disableTexture2D()
        disableAlpha()
        disableBlend()
        enableBlend()
        enableAlpha()
        enableTexture2D()
        enableLighting()
        enableDepth()
        enableAlpha()
        disableStandardItemLighting()
        GL11.glPopMatrix()
    }

    fun updateDelta() {
        val currentTimeMillis = System.currentTimeMillis()
        delta = (System.currentTimeMillis() - lastFrame) / 1000.0f
        lastFrame = currentTimeMillis
    }

    fun drawRect(x: Double, y: Double, width: Double, height: Double, color: Int) {
        enableBlend()
        blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        disableAlpha()
        disableTexture2D()
        color(color)
        val tessellator = instance
        val worldrenderer = tessellator.worldRenderer
        worldrenderer.begin(GL11.GL_QUADS, POSITION())
        worldrenderer.pos(x, y + height, 0.0).endVertex()
        worldrenderer.pos(x + width, y + height, 0.0).endVertex()
        worldrenderer.pos(x + width, y, 0.0).endVertex()
        worldrenderer.pos(x, y, 0.0).endVertex()
        tessellator.draw()
        resetColor()
        enableTexture2D()
        enableAlpha()
        disableBlend()
    }

    fun drawRectFaster(x: Double, y: Double, width: Double, height: Double, color: Int) {
        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glPushMatrix()
        color(color)
        GL11.glBegin(GL11.GL_QUADS)
        GL11.glVertex2d(x + width, y)
        GL11.glVertex2d(x, y)
        GL11.glVertex2d(x, y + height)
        GL11.glVertex2d(x + width, y + height)
        GL11.glEnd()
        GL11.glPopMatrix()
        GL11.glEnable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_BLEND)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glPopMatrix()
        color(-1)
    }

    fun color(color: Int) {
        val f = (color shr 24 and 0xFF) / 255.0f
        val f1 = (color shr 16 and 0xFF) / 255.0f
        val f2 = (color shr 8 and 0xFF) / 255.0f
        val f3 = (color and 0xFF) / 255.0f
        GL11.glColor4f(f1, f2, f3, f)
    }

    @JvmStatic
    fun glDrawFilledQuad(x: Double,
                         y: Double,
                         width: Double,
                         height: Double,
                         colour: Int) {
        // Enable blending
        val restore = glEnableBlend()
        // Disable texture drawing
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        // Set color
        color(colour)

        // Begin rect
        GL11.glBegin(GL11.GL_QUADS)
        run {
            GL11.glVertex2d(x, y)
            GL11.glVertex2d(x, y + height)
            GL11.glVertex2d(x + width, y + height)
            GL11.glVertex2d(x + width, y)
        }
        // Draw the rect
        GL11.glEnd()

        // Disable blending
        glRestoreBlend(restore)
        // Re-enable texture drawing
        GL11.glEnable(GL11.GL_TEXTURE_2D)
    }

    @JvmStatic
    fun glRestoreBlend(wasEnabled: Boolean) {
        if (!wasEnabled) {
            GL11.glDisable(GL11.GL_BLEND)
        }
    }

    @JvmStatic
    fun glEnableBlend(): Boolean {
        val wasEnabled = GL11.glIsEnabled(GL11.GL_BLEND)
        if (!wasEnabled) {
            GL11.glEnable(GL11.GL_BLEND)
            GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0)
        }
        return wasEnabled
    }
}
