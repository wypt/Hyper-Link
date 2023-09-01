package HyperLink.renderer.clickgui.component.components

import HyperLink.HyperLink
import HyperLink.module.Module
import HyperLink.renderer.clickgui.component.BaseComponent
import HyperLink.util.RenderUtil
import HyperLink.util.shader.BlurUtil
import org.lwjgl.input.Keyboard

class BindButton(module: Module, x: Int, y: Int, width: Int, height: Int) : BaseComponent<Module?>(module, x, y, width, height) {
    private var pendingKey = false
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float, offset: Int): Int {
        this.offset = offset
        val y = (y + offset).toFloat()
        BlurUtil.blurArea(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        RenderUtil.drawRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), -0x80000000)
        HyperLink.getInstance().fontManager.Regular18.drawString(if (pendingKey) "PressKey..." else "Bind [" + `object`?.let { Keyboard.getKeyName(it.getKey()) } + "]",
                (
                        x + 5).toDouble(),
                (
                        y + height / 2f - HyperLink.getInstance().fontManager.Regular18.height / 2f).toDouble(),
                -1)
        return height
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (bounding(mouseX, mouseY)) {
            if (mouseButton == 0) pendingKey = !pendingKey
        } else pendingKey = false
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (pendingKey) {
            if (keyCode == Keyboard.KEY_DELETE) `object`?.setKey(Keyboard.KEY_NONE) else `object`?.setKey(keyCode)
            pendingKey = false
        }
    }
}
