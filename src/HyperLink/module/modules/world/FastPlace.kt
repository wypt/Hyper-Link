package HyperLink.module.modules.world

import HyperLink.events.TickEvent
import HyperLink.module.Category
import HyperLink.module.Module
import com.darkmagician6.eventapi.EventTarget

class FastPlace : Module("Fast Place", Category.WORLD) {
    @EventTarget
    fun onTick(event: TickEvent?) {
        if (mc.thePlayer().isNull) return
        mc.rightClickDelayTimer(0)
    }
}
