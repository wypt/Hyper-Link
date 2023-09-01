package HyperLink.wrapper.wrappers

import HyperLink.annotations.WrapperClass
import HyperLink.wrapper.Wrapper

@WrapperClass("net.minecraft.world.World")
class WorldWrapper(`object`: Any?) : Wrapper(`object`) {
    fun loadedEntityList(): List<EntityWrapper> {
        val objects = getFieldValue(WorldWrapper::class.java, "loadedEntityList") as List<Any>
        val wrappers: MutableList<EntityWrapper> = ArrayList()
        for (o in objects) wrappers.add(
                EntityWrapper(
                        o
                ))
        return wrappers
    }
}
