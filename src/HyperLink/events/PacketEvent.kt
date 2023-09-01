//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package HyperLink.events

import HyperLink.wrapper.wrappers.PacketWrapper
import com.darkmagician6.eventapi.events.callables.EventCancellable

class PacketEvent(val side: Side, var packet: PacketWrapper) : EventCancellable() {

    override fun equals(o: Any?): Boolean {
        return if (o === this) {
            true
        } else if (o !is PacketEvent) {
            false
        } else {
            val other = o
            if (!other.canEqual(this)) {
                false
            } else if (!super.equals(o)) {
                false
            } else {
                val `this$side`: Any = side
                val `other$side`: Any = other.side
                if (`this$side` == null) {
                    if (`other$side` != null) {
                        return false
                    }
                } else if (`this$side` != `other$side`) {
                    return false
                }
                val `this$packet`: Any = packet
                val `other$packet`: Any = other.packet
                if (`this$packet` == null) {
                    if (`other$packet` != null) {
                        return false
                    }
                } else if (`this$packet` != `other$packet`) {
                    return false
                }
                true
            }
        }
    }

    protected fun canEqual(other: Any?): Boolean {
        return other is PacketEvent
    }

    override fun toString(): String {
        return "PacketEvent(side=" + side + ", packet=" + packet + ")"
    }

    enum class Side {
        OUT,
        IN
    }
}
