package HyperLink.renderer.clickgui.component.components

import HyperLink.HyperLink
import HyperLink.module.properties.implement.NumberProperty
import HyperLink.renderer.clickgui.component.BaseComponent
import HyperLink.util.MathUtil
import HyperLink.util.RenderUtil
import HyperLink.util.shader.BlurUtil

class Slider(floatSetting: NumberProperty, x: Int, y: Int, width: Int, height: Int) : BaseComponent<NumberProperty?>(floatSetting, x, y, width, height) {
    private val places: Int
    private var isSlide = false

    init {
        places = `object`?.let { Companion.decimalPlaces(it.increment) }!!
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float, offset: Int): Int {
        if (!`object`?.isVisible!!) return 0
        this.offset = offset
        val y = (offset + y).toFloat()
        BlurUtil.blurArea(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        RenderUtil.drawRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), -0x80000000)
        val drawWidth = (`object`.value?.minus(`object`.minimum))?.div((`object`.maximum - `object`.minimum))

        RenderUtil.drawRect(x.toDouble(), y.toDouble(), width * drawWidth!!, height.toDouble(), -0xd48e0d)
        val text = `object`.name + " " + `object`.value
        HyperLink.getInstance().fontManager.Regular18.drawString(text, (x + 5).toDouble(), (y + height / 2f - HyperLink.getInstance().fontManager.Regular18.height / 2f).toDouble(), -1)
        if (isSlide) {
            var value = (mouseX.toFloat() - x) / width
            value = MathUtil.clamp(value, 0.0f, 1.0f)
            val rounded: Double = Companion.round(Math.round((value * (`object`.maximum - `object`.minimum) + `object`.minimum) / `object`.increment) * `object`.increment, places.toDouble())
            `object`.value = rounded
        }
        return height
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (!`object`?.isVisible!!) return
        if (bounding(mouseX, mouseY)) {
            isSlide = true
        }
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        isSlide = false
    }

    companion object {
        // https://github.com/kami-blue/commons/blob/master/org/kamiblue/commons/utils/MathUtils.kt
        private fun round(value: Double, places: Double): Double {
            val scale = Math.pow(10.0, places)
            return Math.round(value * scale) / scale
        }


        fun decimalPlaces(value: Double): Int {
            return value.toString().split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].length
        }
    }
}
