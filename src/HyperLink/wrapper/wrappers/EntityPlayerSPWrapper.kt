package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass

@WrapperClass("net.minecraft.client.entity.EntityPlayerSP")
class EntityPlayerSPWrapper(`object`: Any?) : EntityPlayerWrapper(`object`) {
    fun setSprinting(sprinting: Boolean) {
        invoke("setSprinting", sprinting)
    }
}
