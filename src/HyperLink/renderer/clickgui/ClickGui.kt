package HyperLink.renderer.clickgui

import HyperLink.HyperLink
import HyperLink.module.Category
import HyperLink.renderer.AbstractScreen
import HyperLink.renderer.clickgui.component.CategoryPanel
import org.lwjgl.input.Keyboard
import java.io.IOException
import java.util.function.Consumer

@OptIn(ExperimentalStdlibApi::class)
class ClickGui : AbstractScreen() {
    var panels: MutableList<CategoryPanel> = ArrayList()

    init {
        var x = 10
        for (category in Category.entries) {
            panels.add(CategoryPanel(category, x, 10, 100, 15, HyperLink.getInstance().instanceManager.minecraft))
            x += 110
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        panels.forEach(Consumer { panel: CategoryPanel -> panel.drawScreen(mouseX, mouseY, partialTicks) })
    }

    public override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        panels.forEach(Consumer { panel: CategoryPanel -> panel.mouseClicked(mouseX, mouseY, mouseButton) })
    }

    public override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        panels.forEach(Consumer { panel: CategoryPanel -> panel.mouseReleased(mouseX, mouseY, state) })
    }

    public override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null)
            return
        }
        panels.forEach(Consumer { panel: CategoryPanel -> panel.keyTyped(typedChar, keyCode) })
        try {
            super.keyTyped(typedChar, keyCode)
        } catch (ignored: IOException) {
        }
    }
}
