package com.github.grassproject.folra.util.message

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.impl.JsonConfigFileImpl
import com.github.grassproject.folra.util.message.impl.SimpleMessage
import com.github.grassproject.folra.util.message.impl.view.MessageView
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class FolraTranslate(private val plugin: FolraPlugin) {

    private var jsonCache: JsonObject = loadLanguage()

    private fun loadLanguage(): JsonObject {
        val lang = plugin.config.getString("language") ?: "korean"
        val configFile = JsonConfigFileImpl(plugin, "language/$lang.json")
        return configFile.load()
    }

    fun reload() {
        jsonCache = loadLanguage()
    }

    fun literate(key: String, placeholders: Map<String, String> = emptyMap()): String {
        val raw = jsonCache[key]?.asString ?: key
        return applyPlaceholders(raw, placeholders)
    }

    fun fromList(key: String, placeholders: Map<String, String> = emptyMap()): MutableList<String> {
        val jsonElement = jsonCache[key]
        if (jsonElement !is JsonArray) return mutableListOf(key)

        return jsonElement.mapNotNull { it.asString }
            .map { applyPlaceholders(it, placeholders) }
            .toMutableList()
    }

    fun message(key: String, placeholders: Map<String, String> = emptyMap()): Message {
        val text = literate(key, placeholders)
        return SimpleMessage(listOf(text), MessageView.Chat)
    }

    fun messageList(key: String, placeholders: Map<String, String> = emptyMap()): Message {
        val lines = fromList(key, placeholders)
        return SimpleMessage(lines, MessageView.Chat)
    }

    private fun applyPlaceholders(text: String, placeholders: Map<String, String>): String {
        if (placeholders.isEmpty()) return text
        var result = text
        placeholders.forEach { (key, value) ->
            result = result.replace("{$key}", value)
        }
        return result
    }
}