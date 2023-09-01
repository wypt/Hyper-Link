package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.entity.Entity")
open class EntityWrapper(`object`: Any?) : Wrapper(`object`) {
    fun getDistanceToEntity(entityIn: EntityWrapper): Float {
        return invoke(EntityWrapper::class.java, "getDistanceToEntity", entityIn.`object`) as Float
    }

    val eyeHeight: Float
        get() = invoke(EntityWrapper::class.java, "getEyeHeight") as Float
    val entityBoundingBox: AxisAlignedBBWrapper
        get() = AxisAlignedBBWrapper(invoke(EntityWrapper::class.java, "getEntityBoundingBox"))
    val isCollidedHorizontally: Boolean
        get() = getFieldValue(EntityWrapper::class.java, "isCollidedHorizontally") as Boolean

    fun rotationYaw(): Float {
        return getFieldValue(EntityWrapper::class.java, "rotationYaw") as Float
    }

    fun rotationYaw(value: Float) {
        setFieldValue(EntityWrapper::class.java, "rotationYaw", value)
    }

    fun rotationPitch(): Float {
        return getFieldValue(EntityWrapper::class.java, "rotationPitch") as Float
    }

    fun rotationPitch(value: Float) {
        setFieldValue(EntityWrapper::class.java, "rotationPitch", value)
    }

    fun posX(): Double {
        return getFieldValue(EntityWrapper::class.java, "posX") as Double
    }

    fun posY(): Double {
        return getFieldValue(EntityWrapper::class.java, "posY") as Double
    }

    fun posZ(): Double {
        return getFieldValue(EntityWrapper::class.java, "posZ") as Double
    }

    fun posX(value: Double) {
        setFieldValue(EntityWrapper::class.java, "posX", value)
    }

    fun posY(value: Double) {
        setFieldValue(EntityWrapper::class.java, "posY", value)
    }

    fun posZ(value: Double) {
        setFieldValue(EntityWrapper::class.java, "posZ", value)
    }

    fun onGround(): Boolean {
        return getFieldValue(EntityWrapper::class.java, "onGround") as Boolean
    }

    fun onGround(value: Boolean) {
        setFieldValue(EntityWrapper::class.java, "onGround", value)
    }

    fun motionY(value: Double) {
        setFieldValue(EntityWrapper::class.java, "motionY", value)
    }

    fun motionX(value: Double) {
        setFieldValue(EntityWrapper::class.java, "motionX", value)
    }

    fun motionZ(value: Double) {
        setFieldValue(EntityWrapper::class.java, "motionZ", value)
    }

    fun motionX(): Double {
        return getFieldValue(EntityWrapper::class.java, "motionX") as Double
    }

    fun motionY(): Double {
        return getFieldValue(EntityWrapper::class.java, "motionY") as Double
    }

    fun motionZ(): Double {
        return getFieldValue(EntityWrapper::class.java, "motionZ") as Double
    }

    val name: String
        get() = invoke(EntityWrapper::class.java, "getName") as String
}
