package HyperLink.module.modules.render

import HyperLink.HyperLink
import HyperLink.module.Category
import HyperLink.module.Module
import HyperLink.renderer.clickgui.ClickGui
import org.lwjgl.input.Keyboard

class Menu : Module("Menu", Category.RENDER) {
    private var screen: Any? = null

    init {
        setKey(Keyboard.KEY_RSHIFT)
    }

    protected override fun onEnable() {
        setEnabled(false)
        try {
            if (screen == null) screen = HyperLink.getInstance().screenManager.makeScreen(ClickGui())
            mc.displayGuiScreen(screen)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
