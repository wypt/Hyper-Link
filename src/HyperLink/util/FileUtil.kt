package HyperLink.util

import java.io.*

object FileUtil {
    @JvmStatic
    @Throws(IOException::class)
    fun write(file: File?, bytes: ByteArray?) {
        val stream = FileOutputStream(file)
        stream.write(bytes)
        stream.close()
    }

    @JvmStatic
    fun read(inputStream: InputStream): ByteArray {
        val buffer = ByteArrayOutputStream()
        var readed: Int
        val data = ByteArray(16384)
        try {
            while (inputStream.read(data, 0, data.size).also { readed = it } != -1) {
                buffer.write(data, 0, readed)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.toByteArray()
    }
}
