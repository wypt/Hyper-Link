package HyperLink.util

import HyperLink.HyperLink
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object PlayerUtil {
    private val mc = HyperLink.getInstance().instanceManager.minecraft
    fun strafe(d: Double) {
        if (!isMoving) return
        val yaw = direction
        mc.thePlayer().motionX(-sin(yaw) * d)
        mc.thePlayer().motionZ(cos(yaw) * d)
    }

    fun speed(): Float {
        return sqrt(mc.thePlayer().motionX() * mc.thePlayer().motionX() + mc.thePlayer().motionZ() * mc.thePlayer().motionZ()).toFloat()
    }

    val isMoving: Boolean
        get() = !mc.thePlayer().isNull && (mc.thePlayer().moveForward() != 0f || mc.thePlayer().moveStrafing() != 0f)
    val direction: Double
        get() {
            var rotationYaw = mc.thePlayer().rotationYaw()
            if (mc.thePlayer().moveForward() < 0f) rotationYaw += 180f
            var forward = 1f
            if (mc.thePlayer().moveForward() < 0f) forward = -0.5f else if (mc.thePlayer().moveForward() > 0f) forward = 0.5f
            if (mc.thePlayer().moveStrafing() > 0f) rotationYaw -= 90f * forward
            if (mc.thePlayer().moveStrafing() < 0f) rotationYaw += 90f * forward
            return Math.toRadians(rotationYaw.toDouble())
        }
}
