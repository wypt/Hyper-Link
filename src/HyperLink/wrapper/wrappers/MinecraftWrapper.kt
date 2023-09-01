package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.client.Minecraft")
class MinecraftWrapper(`object`: Any?) : Wrapper(`object`) {
    fun fontRendererObj(): FontRendererWrapper {
        return FontRendererWrapper(getFieldValue("fontRendererObj"))
    }

    fun running(): Boolean {
        return getFieldValue("running") as Boolean
    }

    fun displayWidth(): Int {
        return getFieldValue("displayWidth") as Int
    }

    fun updateFramebufferSize() {
        invoke("updateFramebufferSize")
    }

    fun displayHeight(): Int {
        return getFieldValue("displayHeight") as Int
    }

    fun currentScreen(`object`: Any?) {
        setFieldValue("currentScreen", `object`)
    }

    val framebuffer: FramebufferWrapper
        get() = FramebufferWrapper(invoke("getFramebuffer"))

    fun gameSettings(): GameSettingWrapper {
        return GameSettingWrapper(getFieldValue("gameSettings"))
    }

    fun thePlayer(): EntityPlayerSPWrapper {
        return EntityPlayerSPWrapper(getFieldValue("thePlayer"))
    }

    fun theWorld(): WorldWrapper {
        return WorldWrapper(getFieldValue("theWorld"))
    }

    val netHandler: NetHandlerPlayClientWrapper
        get() = NetHandlerPlayClientWrapper(invoke("getNetHandler"))

    fun displayGuiScreen(wrapper: Any?) {
        invoke("displayGuiScreen", wrapper)
    }

    fun rightClickDelayTimer(value: Int) {
        setFieldValue("rightClickDelayTimer", value)
    }

    companion object {
        val debugFPS: Int
            get() = getStatic(MinecraftWrapper::class.java, "debugFPS") as Int
    }
}
