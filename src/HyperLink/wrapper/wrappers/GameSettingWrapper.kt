package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.client.settings.GameSettings")
class GameSettingWrapper(`object`: Any?) : Wrapper(`object`) {
    fun gammaSetting(value: Float) {
        setFieldValue("gammaSetting", value)
    }
}
