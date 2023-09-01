package HyperLink.renderer.clickgui.component

import HyperLink.HyperLink
import HyperLink.module.Module
import HyperLink.module.properties.implement.BooleanProperly
import HyperLink.module.properties.implement.ColorProperly
import HyperLink.module.properties.implement.ModeProperty
import HyperLink.module.properties.implement.NumberProperty
import HyperLink.renderer.clickgui.component.components.*
import HyperLink.util.RenderUtil
import HyperLink.util.shader.BlurUtil
import HyperLink.wrapper.wrappers.MinecraftWrapper
import java.util.function.Consumer

class ModuleButton(module: Module, x: Int, y: Int, width: Int, height: Int, mc: MinecraftWrapper) {
    private val module: Module
    private val x: Int
    private val y: Int
    private val width: Int
    private val height: Int
    private val mc: MinecraftWrapper
    private val components: MutableList<BaseComponent<*>> = ArrayList()
    private var offset = 0
    private var open = false

    init {
        this.module = module
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        this.mc = mc
        for (property in module.getProperties()) {
            if (property is BooleanProperly) {
                components.add(CheckBox(property as BooleanProperly, x, y, width, height))
            } else if (property is ColorProperly) {
                components.add(ColorPicker(property as ColorProperly, x, y, width, height))
            } else if (property is NumberProperty) {
                components.add(Slider(property as NumberProperty, x, y, width, height))
            } else if (property is ModeProperty) {
                components.add(ComboBox(property as ModeProperty, x, y, width, height))
            }
        }
        components.add(BindButton(module, x, y, width, height))
    }

    fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float, offset: Int): Int {
        this.offset = offset
        val y = y + offset
        RenderUtil.drawRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), -0x80000000)
        BlurUtil.blurArea(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        HyperLink.getInstance().fontManager.Regular18.drawString(module.getName(), (x + 3).toDouble(), (y + (height / 2f - HyperLink.getInstance().fontManager.Regular18.height / 2f)).toDouble(), if (module.isEnabled()) -0xd48e0d else -1)
        var offsets = height
        if (open) {
            for (component in components) {
                offsets += component.drawScreen(mouseX, mouseY, partialTicks, offsets + offset)
            }
        }
        return offsets
    }

    fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (bounding(mouseX, mouseY)) {
            if (mouseButton == 0) {
                module.toggle()
            } else if (mouseButton == 1) {
                open = !open
            }
        }
        if (open) components.forEach(Consumer { item: BaseComponent<*> -> item.mouseClicked(mouseX, mouseY, mouseButton) })
    }

    fun keyTyped(typedChar: Char, keyCode: Int) {
        if (open) components.forEach(Consumer { item: BaseComponent<*> -> item.keyTyped(typedChar, keyCode) })
    }

    fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        if (open) components.forEach(Consumer { item: BaseComponent<*> -> item.mouseReleased(mouseX, mouseY, state) })
    }

    fun bounding(mouseX: Int, mouseY: Int): Boolean {
        if (mouseX < x) return false
        if (mouseX > x + width) return false
        return if (mouseY < y + offset) false else mouseY <= y + offset + height
    }
}
