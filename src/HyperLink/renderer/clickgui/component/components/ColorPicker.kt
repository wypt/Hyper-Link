package HyperLink.renderer.clickgui.component.components

import HyperLink.HyperLink
import HyperLink.module.properties.implement.ColorProperly
import HyperLink.renderer.clickgui.component.BaseComponent
import HyperLink.util.MathUtil
import HyperLink.util.RenderUtil
import HyperLink.util.shader.BlurUtil
import java.awt.Color

class ColorPicker(colorSetting: ColorProperly, x: Int, y: Int, width: Int, height: Int) : BaseComponent<ColorProperly?>(colorSetting, x, y, width, height) {
    private var settingHue: Float
    private var settingSaturation: Float
    private var settingBrightness: Float
    private var movingPicker = false
    private var isSlideHue = false
    private var open: Boolean

    init {
        val hsb = `object`?.value?.let { Color.RGBtoHSB(it.getRed(), `object`.value!!.getGreen(), `object`.value!!.getBlue(), FloatArray(3)) }
        settingHue = (hsb?.get(0) ?: 0) as Float
        settingSaturation = hsb?.get(1)!!
        settingBrightness = (hsb?.get(2) ?: 0) as Float
        open = false
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float, offset: Int): Int {
        if (!`object`?.isVisible!!) return 0
        this.offset = offset
        val y = y + offset
        BlurUtil.blurArea(x.toDouble(), y.toDouble(), width.toDouble(), (height + if (open) width else 0).toDouble())
        RenderUtil.drawRect(x.toDouble(), y.toDouble(), width.toDouble(), (height + if (open) width else 0).toDouble(), -0x80000000)
        RenderUtil.drawRect((x + 2).toDouble(), (y + 2).toDouble(), (height - 4).toDouble(), (height - 4).toDouble(), `object`?.value.hashCode())
        HyperLink.getInstance().fontManager.Regular18.drawString(`object`?.name
                ?: "Module", (x + height + 5).toDouble(), (y + height / 2f - HyperLink.getInstance().fontManager.Regular18.height / 2f).toDouble(), -1)
        if (open) {
            drawColorPicker(x + 3, y + height + 3, width - 6, width - 18, Color.HSBtoRGB(settingHue, settingSaturation, settingBrightness))
            //饱和度 亮度
            RenderUtil.drawRect((x + 3 + (width - 6) * settingSaturation).toDouble(), (y + height + 3 + (width - 18) * (1 - settingBrightness)).toDouble(), 1.0, 1.0, -0x80000000)
            for (hueX in 0 until width - 6) {
                RenderUtil.drawRect((x + 3 + hueX).toDouble(), (y + height + 3 + width - 15).toDouble(), 1.0, 12.0, Color.HSBtoRGB(hueX.toFloat() / (width - 6), 1f, 1f))
            }
            //HUE
            RenderUtil.drawRect((x + 3 + (width - 6) * settingHue).toDouble(), (y + height + 3 + width - 15).toDouble(), 1.0, 12.0, -0x80000000)
            if (movingPicker) {
                settingSaturation = MathUtil.clamp((mouseX.toFloat() - (x + 3)) / (width - 6), 0f, 1f)
                settingBrightness = 1 - MathUtil.clamp((mouseY.toFloat() - (y + height + 3)) / (width - 18), 0f, 1f)
                `object`?.value = Color.getHSBColor(settingHue, settingSaturation, settingBrightness)
            } else if (isSlideHue) {
                settingHue = MathUtil.clamp((mouseX.toFloat() - (x + 3)) / (width - 6), 0f, 1f)
                `object`?.value = Color.getHSBColor(settingHue, settingSaturation, settingBrightness)
            }
        }
        return (if (open) width else 0) + height
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (!`object`?.isVisible!!) return
        if (bounding(mouseX, mouseY, x + 3, y + height + 3, width - 6, width - 18)) movingPicker = true else if (bounding(mouseX, mouseY, x + 3, y + height + 3 + width - 15, width - 6, 12)) isSlideHue = true
        if (bounding(mouseX, mouseY) && mouseButton == 1) {
            open = !open
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        movingPicker = false
        isSlideHue = false
    }

    fun drawColorPicker(x: Int, y: Int, width: Int, height: Int, color: Int) {
        val red = color shr 16 and 0xFF
        val green = color shr 8 and 0xFF
        val blue = color and 0xFF
        val hue = Color.RGBtoHSB(red, green, blue, FloatArray(3))[0]
        for (colorX in 0 until width) {
            for (colorY in 0 until height) {
                val saturation = colorX.toFloat() / width
                val brightness = 1f - colorY.toFloat() / height
                RenderUtil.drawRectFaster((x + colorX).toDouble(), (y + colorY).toDouble(), 1.0, 1.0, Color.HSBtoRGB(hue, saturation, brightness))
            }
        }
    }
}
