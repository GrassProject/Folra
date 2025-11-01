package com.github.grassproject.folra.language

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.util.ConfigFile
import com.github.grassproject.folra.util.message.impl.SimpleMessage
import com.github.grassproject.folra.util.message.impl.view.MessageView
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.bukkit.command.CommandSender

class FolraTranslate(private val plugin: FolraPlugin) {

    private val gson = Gson()
    private lateinit var configFile: ConfigFile
    private lateinit var jsonCache: JsonObject

    fun register() {
        val lang = plugin.config.getString("language") ?: "korean"
        configFile = ConfigFile(plugin, "language/$lang.json")
        if (!configFile.exists()) plugin.saveResource("language/$lang.json", false)
        configFile.load()
        jsonCache = gson.fromJson(configFile.get().saveToString(), JsonObject::class.java)
    }

    fun literate(key: String, placeholders: Map<String, String> = emptyMap()): String {
        val message = jsonCache[key]?.asString ?: key
        return replacePlaceholders(message, placeholders)
    }

    fun fromList(key: String, placeholders: Map<String, String> = emptyMap()): MutableList<String> {
        val element = jsonCache[key]?.takeIf { it.isJsonArray }
        val messages = element?.asJsonArray?.mapNotNull { it.asString }?.map { replacePlaceholders(it, placeholders) }
            ?: listOf(key)
        return messages.toMutableList()
    }

    fun getMessage(key: String, placeholders: Map<String, String> = emptyMap(), view: MessageView = MessageView.Chat): SimpleMessage {
        return SimpleMessage(listOf(literate(key, placeholders)), view)
    }

    fun getMessageList(key: String, placeholders: Map<String, String> = emptyMap(), view: MessageView = MessageView.Chat): SimpleMessage {
        return SimpleMessage(fromList(key, placeholders), view)
    }

    fun sendMessage(sender: CommandSender, key: String, placeholders: Map<String, String> = emptyMap(), view: MessageView = MessageView.Chat) {
        getMessage(key, placeholders, view).send(sender)
    }

    fun sendMessageList(sender: CommandSender, key: String, placeholders: Map<String, String> = emptyMap(), view: MessageView = MessageView.Chat) {
        getMessageList(key, placeholders, view).send(sender)
    }

    private fun replacePlaceholders(message: String, placeholders: Map<String, String>): String {
        var result = message
        placeholders.forEach { (k, v) -> result = result.replace("{$k}", v) }
        return result
    }
}
