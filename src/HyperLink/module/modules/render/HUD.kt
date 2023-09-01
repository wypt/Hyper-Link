package HyperLink.module.modules.render

import HyperLink.HyperLink
import HyperLink.events.Render2DEvent
import HyperLink.module.Category
import HyperLink.module.Module
import HyperLink.module.properties.implement.BooleanProperly
import HyperLink.module.properties.implement.ColorProperly
import HyperLink.util.RenderUtil
import HyperLink.util.shader.BlurUtil
import HyperLink.wrapper.wrappers.MinecraftWrapper
import com.darkmagician6.eventapi.EventTarget
import java.awt.Color

class HUD : Module("HUD", Category.RENDER) {
    private val color = ColorProperly("Main Color", Color.WHITE)
    private val watermark = BooleanProperly("Watermark", true)
    private val arrayList = BooleanProperly("Array List", true)

    init {
        setEnabled(true)
        addProperties(color, watermark, arrayList)
    }

    @EventTarget
    fun onRender2D(event: Render2DEvent) {
        if (mc.thePlayer().isNull()) return
        if (watermark.value!!) {
            val watermark = "HyperLink | " + MinecraftWrapper.debugFPS + "fps | " + mc.thePlayer().name
            BlurUtil.blurArea(4.0, 4.0, (HyperLink.getInstance().fontManager.Regular18.getStringWidth(watermark) + 4).toDouble(), (HyperLink.getInstance().fontManager.Regular18.height + 5).toDouble())
            RenderUtil.drawRect(4.0, 4.0, (HyperLink.getInstance().fontManager.Regular18.getStringWidth(watermark) + 4).toDouble(), (HyperLink.getInstance().fontManager.Regular18.height + 5).toDouble(), Color(0, 0, 0, 100).rgb)
            RenderUtil.drawRect(4.0, 4.0, (HyperLink.getInstance().fontManager.Regular18.getStringWidth(watermark) + 4).toDouble(), 1.0, color.value!!.rgb)
            HyperLink.getInstance().fontManager.Regular18.drawString(watermark, 6.0, 8.0, -1)
        }
        if (arrayList.value!!) {
            val width = event.resolution.scaledWidth
            var y = 4
            val modules = ArrayList(HyperLink.getInstance().moduleManager.modules)
            modules.sortWith(compareBy { m ->
                HyperLink.getInstance().fontManager.Regular18.getStringWidth(m.getName()) -
                        HyperLink.getInstance().fontManager.Regular18.getStringWidth(m.getName())
            })

            for (m in modules) {
                val moduleWidth = HyperLink.getInstance().fontManager.Regular18.getStringWidth(m.getName())
                m.setX(if (m.isEnabled)
                    RenderUtil.getAnimationState(m.getX(), width.toFloat() - moduleWidth.toFloat() - 4, 300f)
                else
                    event.resolution.scaledWidth.toFloat())

                m.setY(if (m.isEnabled)
                    RenderUtil.getAnimationState(m.getY(), y.toFloat(), 300f)
                else
                    -10f)

                color.value?.let {
                    HyperLink.getInstance().fontManager.Regular18.drawStringWithShadow(
                            m.getName(),
                            m.getX().toDouble(),
                            m.getY().toDouble(),
                            it.rgb
                    )
                }

                y += if (m.isEnabled)
                    HyperLink.getInstance().fontManager.Regular18.height + 3
                else
                    0
            }
        }

    }
}
