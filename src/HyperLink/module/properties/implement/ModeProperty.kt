package HyperLink.module.properties.implement

import HyperLink.module.properties.Property
import java.util.*

class ModeProperty(name: String?, value: String, vararg otherModes: String?) : Property<String?>(name, value) {
    val modes: Array<String>

    init {
        val modes: MutableList<String> = ArrayList(Arrays.asList(*otherModes))
        if (!modes.contains(value)) {
            modes.add(value)
        }
        this.modes = modes.toTypedArray<String>()
    }

    fun `is`(string: String): Boolean {
        return value == string
    }
}
