package com.github.grassproject.folra.util.message

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.impl.YamlConfigFile
import com.github.grassproject.folra.util.toMiniMessage
import net.kyori.adventure.text.Component
import java.io.File

open class FolraTranslate(private val plugin: FolraPlugin) {

    private lateinit var langConfig: YamlConfigFile
    var prefix: String = ""
        private set

    fun init() = loadLanguage()
    fun reload() = loadLanguage()

    private fun loadLanguage() {
        val lang = plugin.config.getString("language", "ko-kr")!!
        val fileName = "language/$lang.yml"

        val langFile = File(plugin.dataFolder, fileName)
        if (!langFile.exists()) {
            plugin.saveResource(fileName, false)
        }

        langConfig = YamlConfigFile(plugin, fileName).apply { load() }

        prefix = langConfig.getConfig().getString("prefix", "")!!
        Companion.prefix = prefix
    }

    fun literate(key: String, placeholders: Map<String, String>? = null): String {
        val raw = langConfig.getConfig().getString(key) ?: key
        return raw.applyPlaceholders(prefix, placeholders)
    }

    fun fromList(key: String, placeholders: Map<String, String>? = null): List<String> {
        val rawList = langConfig.getConfig().getStringList(key)
        if (rawList.isEmpty()) return listOf(key)

        return rawList.map { it.applyPlaceholders(prefix, placeholders) }
    }

    fun component(key: String, placeholders: Map<String, String>? = null): Component =
        literate(key, placeholders).toMiniMessage()

    fun componentList(key: String, placeholders: Map<String, String>? = null): List<Component> =
        fromList(key, placeholders).map { it.toMiniMessage() }

    companion object {
        var prefix: String = ""
        fun String.applyPlaceholders(
            customPrefix: String? = null,
            placeholders: Map<String, String>? = null
        ): String {
            if (!this.contains("<prefix>") && (placeholders == null || placeholders.isEmpty())) {
                return this
            }

            var result = this.replace("<prefix>", customPrefix ?: prefix)

            placeholders?.forEach { (k, v) ->
                result = result.replace("{$k}", v)
            }
            return result
        }

        fun String.toComponentWithPlaceholders(
            customPrefix: String? = null,
            placeholders: Map<String, String>? = null
        ): Component = applyPlaceholders(customPrefix, placeholders).toMiniMessage()
    }
}