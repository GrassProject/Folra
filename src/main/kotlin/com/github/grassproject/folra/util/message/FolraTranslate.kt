package com.github.grassproject.folra.util.message

import com.google.gson.JsonObject
import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.impl.JsonConfigFile
import com.github.grassproject.folra.util.toMiniMessage
import net.kyori.adventure.text.Component
import java.io.File

open class FolraTranslate(private val plugin: FolraPlugin) {

    private lateinit var jsonData: JsonObject
    private lateinit var langConfig: JsonConfigFile
    var prefix: String = ""

    fun init() = loadLanguage()
    fun reload() = loadLanguage()

    private fun loadLanguage() {
        plugin.reloadConfig()

        val lang = plugin.config.getString("language") ?: "ko-kr"
        val langFolder = File(plugin.dataFolder, "language").apply { mkdirs() }
        val jsonFile = File(langFolder, "$lang.json")

        if (!jsonFile.exists()) plugin.saveResource("language/$lang.json", false)

        langConfig = JsonConfigFile(plugin, "language/$lang.json").apply { load() }
        jsonData = langConfig.config
        prefix = jsonData.get("prefix")?.asString ?: ""
    }

    private fun applyPlaceholders(message: String, placeholders: Map<String, String>?): String {
        var result = message.replace("<prefix>", prefix)
        placeholders?.forEach { (p, v) -> result = result.replace("{$p}", v) }
        return result
    }

    fun literate(key: String, placeholders: Map<String, String>? = null): String {
        val message = jsonData.get(key)?.asString ?: key
        return applyPlaceholders(message, placeholders)
    }

    fun fromList(key: String, placeholders: Map<String, String>? = null): MutableList<String> {
        val array = jsonData.get(key)?.asJsonArray ?: return mutableListOf()
        return array.mapNotNull { it.asString }
            .map { applyPlaceholders(it, placeholders) }
            .toMutableList()
    }

    fun component(key: String, placeholders: Map<String, String>? = null): Component =
        literate(key, placeholders).toMiniMessage()

    fun componentList(key: String, placeholders: Map<String, String>? = null): List<Component> =
        fromList(key, placeholders).map { it.toMiniMessage() }

//    fun component(
//        key: String,
//        placeholders: Map<String, String>? = null
//    ): Component {
//        return literate(key, placeholders).toComponent()
//    }

}