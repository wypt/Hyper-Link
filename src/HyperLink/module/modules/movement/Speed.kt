package HyperLink.module.modules.movement

import HyperLink.events.MotionUpdateEvent
import HyperLink.module.Category
import HyperLink.module.Module
import HyperLink.module.properties.implement.ModeProperty
import HyperLink.util.PlayerUtil
import com.darkmagician6.eventapi.EventTarget

class Speed : Module("Speed", Category.MOVEMENT) {
    private val mode = ModeProperty("Mode", "YPort", "Jump Strafe")

    init {
        addProperties(mode)
    }

    @EventTarget
    fun onTick(event: MotionUpdateEvent) {
        if (mc.thePlayer().isNull()) return
        if (event.state == MotionUpdateEvent.State.POST) {
            if (PlayerUtil.isMoving) {
                if (mode.`is`("YPort")) {
                    PlayerUtil.strafe(1.0)
                    if (mc.thePlayer().onGround()) mc.thePlayer().jump() else mc.thePlayer().motionY(-1.0)
                }
                if (mode.`is`("Jump Strafe")) {
                    PlayerUtil.strafe(1.3)
                    if (mc.thePlayer().onGround()) mc.thePlayer().jump()
                }
            }
        }
    }
}
