package HyperLink.module.modules.movement

import HyperLink.events.TickEvent
import HyperLink.module.Category
import HyperLink.module.Module
import HyperLink.util.PlayerUtil
import com.darkmagician6.eventapi.EventTarget

class Strafe : Module("Strafe", Category.MOVEMENT) {
    @EventTarget
    fun onTick(event: TickEvent?) {
        if (mc.thePlayer().isNull()) return
        PlayerUtil.strafe(PlayerUtil.speed().toDouble())
    }
}
