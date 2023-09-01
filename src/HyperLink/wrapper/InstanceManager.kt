package HyperLink.wrapper

import HyperLink.wrapper.wrappers.MinecraftWrapper

class InstanceManager(minecraft: Any?) {
    val minecraft: MinecraftWrapper

    init {
        this.minecraft = MinecraftWrapper(minecraft)
    }
}
