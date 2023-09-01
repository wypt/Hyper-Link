package HyperLink.mapping

import HyperLink.HyperLink
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class SrgParser(private val name: String) {
    private val methods: MutableMap<String, String>
    private val fields: MutableMap<String, String>
    private val classes: MutableMap<String, String>

    init {
        methods = HashMap()
        fields = HashMap()
        classes = HashMap()
        parse(name)
    }

    fun getName(): String {
        return if (name.isEmpty()) "MCP" else name
    }

    fun getMethod(name: String): String {
        return methods.getOrDefault(name, name)
    }

    fun getField(name: String): String {
        return fields.getOrDefault(name, name)
    }

    fun getMethodName(name: String): String {
        return methods.getOrDefault(name, name).split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[methods.getOrDefault(name, name).split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1]
    }

    fun getFieldName(name: String): String {
        return fields.getOrDefault(name, name).split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[fields.getOrDefault(name, name).split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1]
    }

    fun getClass(name: String): String {
        return classes.getOrDefault(name, name)
    }

    private fun parse(name: String) {
        if (name.isEmpty()) return
        try {
            BufferedReader(InputStreamReader(Objects.requireNonNull(HyperLink::class.java.getResourceAsStream("/tools/mapping/$name")))).use { reader ->
                var line: String
                while (reader.readLine().also { line = it } != null) {
                    if (line.startsWith("MD: ")) {
                        val parts = line.substring(4).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (parts.size == 4) {
                            methods[parts[0]] = parts[2]
                        }
                    } else if (line.startsWith("FD: ")) {
                        val parts = line.substring(4).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (parts.size == 2) {
                            fields[parts[0]] = parts[1]
                        }
                    } else if (line.startsWith("CL: ")) {
                        val parts = line.substring(4).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (parts.size == 2) {
                            classes[parts[0]] = parts[1]
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
