//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package HyperLink.events

import com.darkmagician6.eventapi.events.Event

class MotionUpdateEvent : Event {
    val state: State
    private var yaw = 0f
    private var pitch = 0f

    @JvmField
    var posX = 0.0

    @JvmField
    var posY = 0.0

    @JvmField
    var posZ = 0.0
    var isOnGround = false
    var isEdited = false

    constructor(state: State) {
        this.state = state
    }

    fun setYaw(yaw: Float) {
        isEdited = true
        this.yaw = yaw
    }

    fun setPitch(pitch: Float) {
        isEdited = true
        this.pitch = pitch
    }

    constructor(state: State, yaw: Float, pitch: Float, posX: Double, posY: Double, posZ: Double, onGround: Boolean, edited: Boolean) {
        this.state = state
        this.yaw = yaw
        this.pitch = pitch
        this.posX = posX
        this.posY = posY
        this.posZ = posZ
        isOnGround = onGround
        isEdited = edited
    }

    fun getYaw(): Float {
        return yaw
    }

    fun getPitch(): Float {
        return pitch
    }

    override fun equals(o: Any?): Boolean {
        return if (o === this) {
            true
        } else if (o !is MotionUpdateEvent) {
            false
        } else {
            val other = o
            if (!other.canEqual(this)) {
                false
            } else if (java.lang.Float.compare(getYaw(), other.getYaw()) != 0) {
                false
            } else if (java.lang.Float.compare(getPitch(), other.getPitch()) != 0) {
                false
            } else if (java.lang.Double.compare(posX, other.posX) != 0) {
                false
            } else if (java.lang.Double.compare(posY, other.posY) != 0) {
                false
            } else if (java.lang.Double.compare(posZ, other.posZ) != 0) {
                false
            } else if (isOnGround != other.isOnGround) {
                false
            } else if (isEdited != other.isEdited) {
                false
            } else {
                val `this$state`: Any = state
                val `other$state`: Any = other.state
                if (`this$state` == null) {
                    if (`other$state` != null) {
                        return false
                    }
                } else if (`this$state` != `other$state`) {
                    return false
                }
                true
            }
        }
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is MotionUpdateEvent
    }

    override fun toString(): String {
        return "MotionUpdateEvent(state=" + state + ", yaw=" + getYaw() + ", pitch=" + getPitch() + ", posX=" + posX + ", posY=" + posY + ", posZ=" + posZ + ", onGround=" + isOnGround + ", edited=" + isEdited + ")"
    }

    enum class State {
        PRE,
        POST
    }
}
