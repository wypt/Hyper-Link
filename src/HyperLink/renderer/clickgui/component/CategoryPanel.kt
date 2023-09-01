package HyperLink.renderer.clickgui.component

import HyperLink.HyperLink
import HyperLink.module.Category
import HyperLink.util.RenderUtil
import HyperLink.wrapper.wrappers.MinecraftWrapper
import java.util.function.Consumer

class CategoryPanel(category: Category, x: Int, y: Int, width: Int, height: Int, mc: MinecraftWrapper) {
    private val category: Category
    private val x: Int
    private val y: Int
    private val width: Int
    private val height: Int
    private val mc: MinecraftWrapper
    private val moduleButtons: MutableList<ModuleButton> = ArrayList()
    private var open = true

    init {
        this.category = category
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        this.mc = mc
        for (module in HyperLink.getInstance().moduleManager.modules) {
            if (module.category === this.category) {
                moduleButtons.add(ModuleButton(module, x, y, width, height, mc))
            }
        }
    }

    fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        RenderUtil.drawRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), -0xd48e0d)
        HyperLink.getInstance().fontManager.Regular18.drawString(category.toString(),
                (
                        x + width / 2f - HyperLink.getInstance().fontManager.Regular18.getStringWidth(category.toString()) / 2f).toDouble(),
                (
                        y + height / 2f - HyperLink.getInstance().fontManager.Regular18.height / 2f).toDouble(),
                -1)
        if (open) {
            var offset = height
            for (moduleButton in moduleButtons) {
                offset += moduleButton.drawScreen(mouseX, mouseY, partialTicks, offset)
            }
        }
    }

    fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (open) moduleButtons.forEach(Consumer { moduleButton: ModuleButton -> moduleButton.mouseClicked(mouseX, mouseY, mouseButton) })
        if (bounding(mouseX, mouseY) && mouseButton == 1) open = !open
    }

    fun keyTyped(typedChar: Char, keyCode: Int) {
        if (open) moduleButtons.forEach(Consumer { moduleButton: ModuleButton -> moduleButton.keyTyped(typedChar, keyCode) })
    }

    fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        if (open) moduleButtons.forEach(Consumer { moduleButton: ModuleButton -> moduleButton.mouseReleased(mouseX, mouseY, state) })
    }

    fun bounding(mouseX: Int, mouseY: Int): Boolean {
        if (mouseX < x) return false
        if (mouseX > x + width) return false
        return if (mouseY < y) false else mouseY <= y + height
    }
}
