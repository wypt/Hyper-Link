package HyperLink.renderer.clickgui.component

import HyperLink.HyperLink

abstract class BaseComponent<T>(val `object`: T, val x: Int, val y: Int, var width: Int, var height: Int) {
    var offset = 0
    var mc = HyperLink.getInstance().instanceManager.minecraft
    open fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float, offset: Int): Int {
        return 0
    }

    open fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {}
    open fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {}
    open fun keyTyped(typedChar: Char, keyCode: Int) {}
    fun bounding(mouseX: Int, mouseY: Int): Boolean {
        if (mouseX < x) return false
        if (mouseX > x + width) return false
        return if (mouseY < y + offset) false else mouseY <= y + offset + height
    }

    fun bounding(mouseX: Int, mouseY: Int, x: Int, y: Int, width: Int, height: Int): Boolean {
        if (mouseX < x) return false
        if (mouseX > x + width) return false
        return if (mouseY < y + offset) false else mouseY <= y + offset + height
    }
}
