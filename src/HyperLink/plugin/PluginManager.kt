package HyperLink.plugin

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class PluginManager {
    fun loadPlugins() {
        if (!plugins.exists()) {
            plugins.mkdirs()
        }
        for (file in Objects.requireNonNull(plugins.listFiles())) {
            if (file.getName().endsWith(".jar")) {
                try {
                    val fileInputStream = FileInputStream(file)
                    fileInputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                try {
                    JavaPlugin(file)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        val plugins = File("plugins/")
    }
}