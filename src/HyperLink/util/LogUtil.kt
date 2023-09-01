package HyperLink.util

import java.text.SimpleDateFormat
import java.util.*

object LogUtil {
    @JvmStatic
    fun log(message: String) {
        println(formatDate + message)
    }

    @JvmStatic
    val formatDate: String
        get() {
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            val formattedDate = dateFormat.format(currentDate)
            return "[$formattedDate] "
        }
}
