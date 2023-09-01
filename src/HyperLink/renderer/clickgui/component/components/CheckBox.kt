package HyperLink.renderer.clickgui.component.components

import HyperLink.HyperLink
import HyperLink.module.properties.implement.BooleanProperly
import HyperLink.renderer.clickgui.component.BaseComponent
import HyperLink.util.RenderUtil
import HyperLink.util.shader.BlurUtil

class CheckBox(boolSetting: BooleanProperly, x: Int, y: Int, width: Int, height: Int) : BaseComponent<BooleanProperly?>(boolSetting, x, y, width, height) {
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float, offset: Int): Int {
        if (!`object`?.isVisible!!) return 0
        this.offset = offset
        val y = (y + offset).toFloat()
        BlurUtil.blurArea(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        RenderUtil.drawRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), -0x80000000)
        HyperLink.getInstance().fontManager.Regular18.drawString(`object`.name, (x + 5).toDouble(), (y + height / 2f - HyperLink.getInstance().fontManager.Regular18.height / 2f).toDouble(), if (`object`.value == true) -0xd48e0d else -1)
        return height
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (!`object`?.isVisible!!) return
        if (bounding(mouseX, mouseY)) {
            if (mouseButton == 0) `object`?.value = !`object`?.value!!
        }
    }
}
