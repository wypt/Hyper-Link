package HyperLink.renderer

import HyperLink.HyperLink
import java.io.IOException

abstract class AbstractScreen {
    open fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {}

    @Throws(IOException::class)
    protected open fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
    }

    protected open fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {}
    protected fun mouseClickMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) {}

    @Throws(IOException::class)
    fun handleMouseInput() {
    }

    @Throws(IOException::class)
    protected open fun keyTyped(typedChar: Char, keyCode: Int) {
    }

    companion object {
        @JvmStatic
        protected val mc = HyperLink.getInstance().instanceManager.minecraft
    }
}
