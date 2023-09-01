package HyperLink.module.modules.movement

import HyperLink.events.TickEvent
import HyperLink.module.Category
import HyperLink.module.Module
import com.darkmagician6.eventapi.EventTarget

class Sprint : Module("Sprint", Category.MOVEMENT) {
    @EventTarget
    fun onTick(event: TickEvent?) {
        if (mc.thePlayer().isNull()) return
        if (!mc.thePlayer().isCollidedHorizontally && mc.thePlayer().moveForward() > 0) {
            mc.thePlayer().setSprinting(true)
        }
    }
}
