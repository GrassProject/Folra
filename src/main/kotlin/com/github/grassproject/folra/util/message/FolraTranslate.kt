package com.github.grassproject.folra.util.message

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.impl.JsonConfigFile
import com.github.grassproject.folra.util.toMiniMessage
import com.google.gson.JsonObject
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
        jsonData = langConfig.getConfig()
        prefix = jsonData.get("prefix")?.asString ?: ""
        Companion.prefix = prefix
    }

    fun literate(key: String, placeholders: Map<String, String>? = null): String =
        (jsonData.get(key)?.asString ?: key).applyPlaceholders(prefix, placeholders)

    fun fromList(key: String, placeholders: Map<String, String>? = null): List<String> =
        jsonData.get(key)?.asJsonArray
            ?.map { it.asString.applyPlaceholders(prefix, placeholders) }
            ?: emptyList()

    fun component(key: String, placeholders: Map<String, String>? = null): Component =
        literate(key, placeholders).toMiniMessage()

    fun componentList(key: String, placeholders: Map<String, String>? = null): List<Component> =
        fromList(key, placeholders).map { it.toMiniMessage() }

    companion object {
        var prefix: String = ""

        fun String.applyPlaceholders(
            prefix: String? = null,
            placeholders: Map<String, String>? = null
        ): String {
            val usePrefix = prefix ?: Companion.prefix
            var result = replace("<prefix>", usePrefix)
            placeholders?.forEach { (p, v) -> result = result.replace("{$p}", v) }
            return result
        }

        fun String.toComponentWithPlaceholders(
            prefix: String? = null,
            placeholders: Map<String, String>? = null
        ): Component = applyPlaceholders(prefix, placeholders).toMiniMessage()
    }

}