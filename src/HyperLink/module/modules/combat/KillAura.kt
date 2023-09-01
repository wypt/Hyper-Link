package HyperLink.module.modules.combat

import HyperLink.events.MotionUpdateEvent
import HyperLink.module.Category
import HyperLink.module.Module
import HyperLink.module.properties.implement.NumberProperty
import HyperLink.util.MathUtil
import HyperLink.util.TimerUtil
import HyperLink.wrapper.Wrapper
import HyperLink.wrapper.wrappers.*
import com.darkmagician6.eventapi.EventTarget
import java.util.function.Predicate
import java.util.function.ToDoubleFunction

class KillAura : Module("Kill Aura", Category.COMBAT) {
    private val range = NumberProperty("Range", 4.5, 3.0, 6.0, 0.1)
    private val aps = NumberProperty("APS", 15.0, 1.0, 20.0, 1.0)
    private var target: EntityLivingBaseWrapper? = null
    private val timer = TimerUtil()

    init {
        addProperties(range, aps)
    }

    @EventTarget
    fun onMotion(event: MotionUpdateEvent) {
        if (event.state == MotionUpdateEvent.State.PRE) {
            val objects: Array<Any> = mc.theWorld().loadedEntityList()
                    .stream()
                    .filter(Predicate<EntityWrapper> { entity: EntityWrapper -> entity.isInstance(Wrapper.allocateClass(EntityLivingBaseWrapper::class.java)) && entity != mc.thePlayer() && EntityLivingBaseWrapper(entity).health > 0f && entity.getDistanceToEntity(mc.thePlayer()) <= range.value!! })
                    .sorted(Comparator.comparingDouble<EntityWrapper>(ToDoubleFunction<EntityWrapper> { entity: EntityWrapper -> entity.getDistanceToEntity(mc.thePlayer()).toDouble() }))
                    .toArray()
            if (objects.size > 0) target = EntityPlayerSPWrapper(objects[0])
            if (target != null) {
                event.setYaw(getNeededRotations(target!!)[0])
                event.setPitch(getNeededRotations(target!!)[1])
            }
        } else {
            if (target == null) return
            if (timer.hasReached((1000 / aps.value!!.toFloat()).toDouble())) {
                timer.reset()
                mc.thePlayer().swingItem()
                mc.netHandler.addToSendQueue(C02PacketUseEntityWrapper(EntityWrapper(target!!.getObject()), C02PacketUseEntityActionWrapper.ATTACK()))
            }
            target = null
        }
    }

    companion object {
        fun getNeededRotations(entity: EntityWrapper): FloatArray {
            val x: Double = entity.posX() - mc.thePlayer().posX()
            val y: Double = entity.posZ() - mc.thePlayer().posZ()
            val z: Double = entity.posY() + entity.eyeHeight - (mc.thePlayer().entityBoundingBox.minY() + (mc.thePlayer().entityBoundingBox.maxY() - mc.thePlayer().entityBoundingBox.minY()))
            val pos = MathUtil.sqrt_double(x * x + y * y).toDouble()
            val yaw = (MathHelperWrapper.atan2(y, x) * 180.0 / Math.PI).toFloat() - 90.0f
            val pitch = (-(MathHelperWrapper.atan2(z, pos) * 180.0 / Math.PI)).toFloat()
            return floatArrayOf(yaw, pitch)
        }
    }
}
