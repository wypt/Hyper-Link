package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper
import HyperLink.wrapper.wrappers.FramebufferWrapper

@WrapperClass("net.minecraft.client.shader.Framebuffer")
class FramebufferWrapper : Wrapper {
    constructor(`object`: Any?) : super(`object`)
    constructor(p_i45078_1_: Int, p_i45078_2_: Int, p_i45078_3_: Boolean) : super(createObject(allocateClass(FramebufferWrapper::class.java).getConstructor(Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, Boolean::class.javaPrimitiveType), p_i45078_1_, p_i45078_2_, p_i45078_3_))

    fun framebufferClear() {
        invoke("framebufferClear")
    }

    fun deleteFramebuffer() {
        invoke("deleteFramebuffer")
    }

    fun bindFramebuffer(p_147610_1_: Boolean) {
        invoke("bindFramebuffer", p_147610_1_)
    }

    fun framebufferTexture(): Int {
        return getFieldValue("framebufferTexture") as Int
    }
}
