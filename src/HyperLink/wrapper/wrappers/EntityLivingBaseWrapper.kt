package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass

@WrapperClass("net.minecraft.entity.EntityLivingBase")
open class EntityLivingBaseWrapper(`object`: Any?) : EntityWrapper(`object`) {
    val health: Float
        get() = invoke(EntityLivingBaseWrapper::class.java, "getHealth") as Float

    fun rotationYawHead(value: Float) {
        setFieldValue(EntityLivingBaseWrapper::class.java, "rotationYawHead", value)
    }

    fun renderYawOffset(value: Float) {
        setFieldValue(EntityLivingBaseWrapper::class.java, "renderYawOffset", value)
    }

    fun swingItem() {
        invoke(EntityLivingBaseWrapper::class.java, "swingItem")
    }

    fun moveForward(): Float {
        return getFieldValue(EntityLivingBaseWrapper::class.java, "moveForward") as Float
    }

    fun moveStrafing(): Float {
        return getFieldValue(EntityLivingBaseWrapper::class.java, "moveStrafing") as Float
    }

    fun jump() {
        invoke(EntityLivingBaseWrapper::class.java, "jump")
    }
}
