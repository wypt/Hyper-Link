package HyperLink.util

import kotlin.math.min
import kotlin.math.sqrt

object MathUtil {
    fun clamp(value: Float, min: Float, max: Float): Float {
        return if (value < min) {
            min
        } else min(value.toDouble(), max.toDouble()).toFloat()
    }

    fun sqrt_double(value: Double): Float {
        return sqrt(value).toFloat()
    }
}
