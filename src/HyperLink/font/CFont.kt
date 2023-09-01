package HyperLink.font

import HyperLink.wrapper.wrappers.DynamicTextureWrapper
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

open class CFont {
    @JvmField
    protected var charmap: HashMap<Char?, Char?> = HashMap()
    private var imgSize = 512f

    @JvmField
    protected var charData = arrayOfNulls<CharData>(256)

    @JvmField
    protected var font: Font

    @JvmField
    protected var antiAlias: Boolean

    @JvmField
    protected var fractionalMetrics: Boolean
    protected var fontHeight = -1

    @JvmField
    protected var charOffset = 0

    @JvmField
    protected var tex: DynamicTextureWrapper?
    private var chinese: Boolean
    private var hasDone: Boolean

    constructor(font: Font, antiAlias: Boolean, fractionalMetrics: Boolean) {
        this.font = font
        this.antiAlias = antiAlias
        this.fractionalMetrics = fractionalMetrics
        chinese = false
        hasDone = false
        tex = setupTexture(font, antiAlias, fractionalMetrics, charData)
    }

    constructor(font: Font, antiAlias: Boolean, fractionalMetrics: Boolean, chinese: Boolean) {
        this.font = font
        this.antiAlias = antiAlias
        this.fractionalMetrics = fractionalMetrics
        this.chinese = chinese
        hasDone = false
        if (this.chinese) {
            imgSize = 1792f
            charData = arrayOfNulls(5000)
        }
        tex = setupTexture(font, antiAlias, fractionalMetrics, charData)
    }

    protected fun setupTexture(font: Font?, antiAlias: Boolean, fractionalMetrics: Boolean, chars: Array<CharData?>): DynamicTextureWrapper? {
        val img = generateFontImage(font, antiAlias, fractionalMetrics, chars)
        try {
            return DynamicTextureWrapper(img)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    protected fun generateFontImage(font: Font?, antiAlias: Boolean, fractionalMetrics: Boolean,
                                    chars: Array<CharData?>): BufferedImage {
        val imgSize = imgSize.toInt()
        val bufferedImage = BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB)
        val g = bufferedImage.graphics as Graphics2D
        g.font = font
        g.color = Color(255, 255, 255, 0)
        g.fillRect(0, 0, imgSize, imgSize)
        g.color = Color.WHITE
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                if (fractionalMetrics) RenderingHints.VALUE_FRACTIONALMETRICS_ON else RenderingHints.VALUE_FRACTIONALMETRICS_OFF)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                if (antiAlias) RenderingHints.VALUE_TEXT_ANTIALIAS_ON else RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                if (antiAlias) RenderingHints.VALUE_ANTIALIAS_ON else RenderingHints.VALUE_ANTIALIAS_OFF)
        val fontMetrics = g.fontMetrics
        var charHeight = 0
        var positionX = 0
        var positionY = 1
        for (i in 0..255) {
            val ch = i.toChar()
            val charData = CharData()
            val dimensions = fontMetrics.getStringBounds(ch.toString(), g)
            charData.width = dimensions.getBounds().width + 8
            charData.height = dimensions.getBounds().height
            if (positionX + charData.width >= imgSize) {
                positionX = 0
                positionY += charHeight
                charHeight = 0
            }
            if (charData.height > charHeight) {
                charHeight = charData.height
            }
            charData.storedX = positionX
            charData.storedY = positionY
            if (charData.height > fontHeight) {
                fontHeight = charData.height
            }
            chars[i] = charData
            g.drawString(ch.toString(), positionX + 2, positionY + fontMetrics.ascent)
            positionX += charData.width
        }
        if (chinese && !hasDone) {
            val chinesechar = Chars.chars
            for (i in 256 until 255 + chinesechar.size) {
                val ch = i.toChar()
                charmap[chinesechar[i - 256]] = (i - 256).toChar()
                val charData = CharData()
                val dimensions = fontMetrics.getStringBounds(chinesechar[i - 256].toString(), g)
                charData.width = dimensions.getBounds().width + 8
                charData.height = dimensions.getBounds().height
                if (positionX + charData.width >= imgSize) {
                    positionX = 0
                    positionY += charHeight
                    charHeight = 0
                }
                if (charData.height > charHeight) {
                    charHeight = charData.height
                }
                charData.storedX = positionX
                charData.storedY = positionY
                if (charData.height > fontHeight) {
                    fontHeight = charData.height
                }
                chars[i] = charData
                g.drawString(chinesechar[i - 256].toString(), positionX + 2, positionY + fontMetrics.ascent)
                positionX += charData.width
            }
            hasDone = true
        }
        return bufferedImage
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    fun drawChar(chars: Array<CharData>, c: Char, x: Float, y: Float) {
        try {
            drawQuad(x, y, chars[c.code].width.toFloat(), chars[c.code].height.toFloat(), chars[c.code].storedX.toFloat(), chars[c.code].storedY.toFloat(), chars[c.code].width.toFloat(),
                    chars[c.code].height.toFloat())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun drawQuad(x: Float, y: Float, width: Float, height: Float, srcX: Float, srcY: Float, srcWidth: Float,
                           srcHeight: Float) {
        val renderSRCX = srcX / imgSize
        val renderSRCY = srcY / imgSize
        val renderSRCWidth = srcWidth / imgSize
        val renderSRCHeight = srcHeight / imgSize
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY)
        GL11.glVertex2d((x + width).toDouble(), y.toDouble())
        GL11.glTexCoord2f(renderSRCX, renderSRCY)
        GL11.glVertex2d(x.toDouble(), y.toDouble())
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight)
        GL11.glVertex2d(x.toDouble(), (y + height).toDouble())
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight)
        GL11.glVertex2d(x.toDouble(), (y + height).toDouble())
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight)
        GL11.glVertex2d((x + width).toDouble(), (y + height).toDouble())
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY)
        GL11.glVertex2d((x + width).toDouble(), y.toDouble())
    }

    fun getStringHeight(text: String?): Int {
        return height
    }

    val height: Int
        get() = (fontHeight - 8) / 2

    open fun getStringWidth(text: String): Int {
        var width = 0
        for (c in text.toCharArray()) {
            if (c.code < charData.size && c.code >= 0) {
                width += charData[c.code]!!.width - 8 + charOffset
            }
        }
        return width / 2
    }

    fun isAntiAlias(): Boolean {
        return antiAlias
    }

    open fun setAntiAlias(antiAlias: Boolean) {
        if (this.antiAlias != antiAlias) {
            this.antiAlias = antiAlias
            tex = setupTexture(font, antiAlias, fractionalMetrics, charData)
        }
    }

    fun isFractionalMetrics(): Boolean {
        return fractionalMetrics
    }

    open fun setFractionalMetrics(fractionalMetrics: Boolean) {
        if (this.fractionalMetrics != fractionalMetrics) {
            this.fractionalMetrics = fractionalMetrics
            tex = setupTexture(font, antiAlias, fractionalMetrics, charData)
        }
    }

    fun getFont(): Font {
        return font
    }

    open fun setFont(font: Font) {
        this.font = font
        tex = setupTexture(font, antiAlias, fractionalMetrics, charData)
    }

    inner class CharData {
        @JvmField
        var width = 0

        @JvmField
        var height = 0
        var storedX = 0
        var storedY = 0
    }
}