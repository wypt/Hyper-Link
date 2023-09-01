package HyperLink.plugin

import HyperLink.annotations.InitializationCall
import java.io.File
import java.lang.reflect.InvocationTargetException
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarEntry
import java.util.jar.JarFile

class JavaPlugin(file: File) {
    val classMap = LinkedHashMap<String, Class<*>>()

    init {
        val urlClassLoader = URLClassLoader(arrayOf(URL("file:" + file.absolutePath)))
        val jarFile = JarFile(file)
        val entries = jarFile.entries()
        var entry: JarEntry
        while (entries.hasMoreElements()) {
            entry = entries.nextElement()
            if (entry.name.endsWith(".class")) {
                try {
                    val name = entry.name.split(".class".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].replace("/".toRegex(), ".")
                    val aClass = urlClassLoader.loadClass(name)
                    classMap[name] = aClass
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
        jarFile.close()
        for (value in classMap.values) {
            for (declaredMethod in value.getDeclaredMethods()) {
                if (!declaredMethod.isAccessible) {
                    declaredMethod.setAccessible(true)
                }
                val annotation = declaredMethod.getDeclaredAnnotation(InitializationCall::class.java)
                if (annotation != null) {
                    try {
                        declaredMethod.invoke(null)
                    } catch (e: IllegalAccessException) {
                        e.printStackTrace()
                    } catch (e: InvocationTargetException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
