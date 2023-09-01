package HyperLink.config

import HyperLink.HyperLink
import HyperLink.module.properties.implement.BooleanProperly
import HyperLink.module.properties.implement.ColorProperly
import HyperLink.module.properties.implement.ModeProperty
import HyperLink.module.properties.implement.NumberProperty
import HyperLink.util.FileUtil
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.awt.Color
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

class Config(val name: String) {
    protected val gson = GsonBuilder().setPrettyPrinting().create()
    val file: File

    init {
        file = File("$name.json")
    }

    fun load() {
        if (!file.exists()) {
            return
        }
        try {
            val jsonContent = String(FileUtil.read(Files.newInputStream(file.toPath())))
            val parser = JsonParser()
            val jsonObject = parser.parse(jsonContent).asJsonObject
            for (module in HyperLink.getInstance().moduleManager.modules) {
                if (jsonObject.has(module.name)) {
                    val moduleConfig = jsonObject[module.name].asJsonObject
                    module.isEnabled = moduleConfig["enabled"].asBoolean
                    module.key = moduleConfig["key"].asInt
                    module.isHidden = moduleConfig["hidden"].asBoolean
                    if (moduleConfig.has("properties")) {
                        val properties = moduleConfig["properties"].asJsonObject
                        for (property in module.properties) {
                            if (property is BooleanProperly) {
                                property.value = properties[property.name].asBoolean
                            }
                            if (property is NumberProperty) {
                                property.value = properties[property.name].asDouble
                            }
                            if (property is ColorProperly) {
                                property.value = Color(properties[property.name].asInt)
                            }
                            if (property is ModeProperty) {
                                property.value = properties[property.name].asString
                            }
                        }
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    fun save() {
        try {
            val config = JsonObject()
            for (module in HyperLink.getInstance().moduleManager.modules) {
                val moduleJson = JsonObject()
                val propertiesJson = JsonObject()
                moduleJson.addProperty("enabled", module.isEnabled)
                moduleJson.addProperty("hidden", module.isHidden)
                moduleJson.addProperty("key", module.key)
                if (!module.properties.isEmpty()) {
                    for (property in module.properties) {
                        if (property is BooleanProperly) {
                            propertiesJson.addProperty(property.name, property.value)
                        }
                        if (property is NumberProperty) {
                            propertiesJson.addProperty(property.name, property.value)
                        }
                        if (property is ColorProperly) {
                            propertiesJson.addProperty(property.name, property.value!!.rgb)
                        }
                        if (property is ModeProperty) {
                            propertiesJson.addProperty(property.name, property.value)
                        }
                    }
                    moduleJson.add("properties", propertiesJson)
                }
                config.add(module.name, moduleJson)
            }
            FileUtil.write(file, gson.toJson(config).toByteArray(StandardCharsets.UTF_8))
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }
}
