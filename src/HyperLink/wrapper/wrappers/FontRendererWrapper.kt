package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.client.gui.FontRenderer")
class FontRendererWrapper(`object`: Any?) : Wrapper(`object`) {
    fun drawStringWithShadow(text: String?, x: Float, y: Float, color: Int): Int {
        return invoke("drawStringWithShadow", text, x, y, color) as Int
    }

    fun FONT_HEIGHT(): Int {
        return 9
    }

    fun getStringWidth(text: String?): Int {
        return invoke("getStringWidth", text) as Int
    }
}
