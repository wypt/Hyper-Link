package HyperLink.module.modules.combat

import HyperLink.events.PacketEvent
import HyperLink.module.Category
import HyperLink.module.Module
import HyperLink.wrapper.wrappers.S12PacketEntityVelocityWrapper
import com.darkmagician6.eventapi.EventTarget

class Velocity : Module("Velocity", Category.COMBAT) {
    @EventTarget
    fun onPacket(event: PacketEvent) {
        if (event.side == PacketEvent.Side.IN) {
            if (event.packet.`is`(S12PacketEntityVelocityWrapper::class.java)) {
                event.isCancelled = true
            }
        }
    }
}
