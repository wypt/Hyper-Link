package HyperLink.injection.impl

import HyperLink.HyperLink
import HyperLink.annotations.Inject
import HyperLink.events.MotionUpdateEvent
import HyperLink.events.PlayerUpdateEvent
import HyperLink.injection.AbstractHook
import HyperLink.util.ASMUtil
import com.darkmagician6.eventapi.EventManager
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodInsnNode

@Inject("net/minecraft/client/entity/EntityPlayerSP")
@Suppress("unused")
class EntityPlayerSPHook : AbstractHook() {
    override fun hook(cn: ClassNode) {
        val onUpdate = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/entity/EntityPlayerSP/onUpdate"), "()V")
        val onUpdateWalkingPlayer = ASMUtil.getMethod(cn, HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/entity/EntityPlayerSP/onUpdateWalkingPlayer"), "()V")
        for (instruction in onUpdate.instructions) {
            if (instruction !is MethodInsnNode) {
                continue
            }
            val min = instruction
            if (min.name == HyperLink.getInstance().mapping.getMethodName("net/minecraft/client/entity/EntityPlayerSP/onUpdate")) {
                onUpdate.instructions.insertBefore(min.previous, MethodInsnNode(Opcodes.INVOKESTATIC, EntityPlayerSPHook::class.java.getName().replace(".", "/"), "onUpdateEventCall", "()V", false))
            }
        }
        onUpdateWalkingPlayer.instructions.insertBefore(onUpdateWalkingPlayer.instructions.first, MethodInsnNode(Opcodes.INVOKESTATIC, EntityPlayerSPHook::class.java.getName().replace(".", "/"), "preUpdateCall", "()V", false))
        onUpdateWalkingPlayer.instructions.insertBefore(onUpdateWalkingPlayer.instructions.last.previous, MethodInsnNode(Opcodes.INVOKESTATIC, EntityPlayerSPHook::class.java.getName().replace(".", "/"), "postUpdateCall", "()V", false))
    }

    companion object {
        private var yaw = 0f
        private var pitch = 0f
        private var x = 0.0
        private var y = 0.0
        private var z = 0.0
        private var onGround = false

        @JvmStatic
        fun preUpdateCall() {
            val player = HyperLink.getInstance().instanceManager.minecraft.thePlayer()
            val event = MotionUpdateEvent(MotionUpdateEvent.State.PRE, player.rotationYaw(), player.rotationPitch(), player.posX(), player.posY(), player.posZ(), player.onGround(), false)
            cacheData(event)
            EventManager.call(event)
            player.rotationYaw(event.getYaw())
            player.rotationPitch(event.getPitch())
            player.posX(event.posX)
            player.posY(event.posY)
            player.posZ(event.posZ)
            player.onGround(event.isOnGround)
            if (event.isEdited) {
                player.rotationYawHead(event.getYaw())
                player.renderYawOffset(event.getYaw())
            }
        }

        @JvmStatic
        private fun cacheData(event: MotionUpdateEvent) {
            val player = HyperLink.getInstance().instanceManager.minecraft.thePlayer()
            player.rotationYaw(event.getYaw().also { yaw = it })
            player.rotationPitch(event.getPitch().also { pitch = it })
            player.posX(event.posX.also { x = it })
            player.posY(event.posY.also { y = it })
            player.posZ(event.posZ.also { z = it })
            player.onGround(event.isOnGround.also { onGround = it })
        }

        @JvmStatic
        fun postUpdateCall() {
            val player = HyperLink.getInstance().instanceManager.minecraft.thePlayer()
            player.rotationYaw(yaw)
            player.rotationPitch(pitch)
            player.posX(x)
            player.posY(y)
            player.posZ(z)
            player.onGround(onGround)
            val event = MotionUpdateEvent(MotionUpdateEvent.State.POST, player.rotationYaw(), player.rotationPitch(), player.posX(), player.posY(), player.posZ(), player.onGround(), false)
            EventManager.call(event)
            if (event.isEdited) {
                player.rotationYaw(event.getYaw())
                player.rotationPitch(event.getPitch())
                player.posX(event.posX)
                player.posY(event.posY)
                player.posZ(event.posZ)
                player.onGround(event.isOnGround)
            }
        }

        @JvmStatic
        fun onUpdateEventCall() {
            EventManager.call(PlayerUpdateEvent())
        }
    }
}
