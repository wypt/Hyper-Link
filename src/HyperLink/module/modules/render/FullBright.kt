package HyperLink.module.modules.render

import HyperLink.module.Category
import HyperLink.module.Module

class FullBright : Module("Full Bright", Category.RENDER) {
    override fun onEnable() {
        mc.gameSettings().gammaSetting(300f)
    }

    override fun onDisable() {
        mc.gameSettings().gammaSetting(1f)
    }
}
