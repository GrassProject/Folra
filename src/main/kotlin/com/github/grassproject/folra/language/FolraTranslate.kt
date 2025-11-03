package com.github.grassproject.folra.language

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.impl.JsonConfigFileImpl
import com.github.grassproject.folra.util.message.impl.SimpleMessage
import com.github.grassproject.folra.util.message.impl.view.MessageView
import com.google.gson.JsonObject
import org.bukkit.command.CommandSender

class FolraTranslate(plugin: FolraPlugin) {

    private val jsonCache: JsonObject = run {
        val lang = plugin.config.getString("language") ?: "korean"
        val configFile = JsonConfigFileImpl(plugin, "language/$lang.json")
        configFile.load()
    }

    fun literate(key: String, placeholders: Map<String, String> = emptyMap()): String =
        jsonCache[key]?.asString?.let { replacePlaceholders(it, placeholders) } ?: key

    fun fromList(key: String, placeholders: Map<String, String> = emptyMap()): MutableList<String> =
        jsonCache[key]?.takeIf { it.isJsonArray }
            ?.asJsonArray?.mapNotNull { it.asString }?.map { replacePlaceholders(it, placeholders) }
            ?.toMutableList() ?: mutableListOf(key)

    fun getMessage(key: String, placeholders: Map<String, String> = emptyMap(), view: MessageView = MessageView.Chat) =
        SimpleMessage(listOf(literate(key, placeholders)), view)

    fun getMessageList(key: String, placeholders: Map<String, String> = emptyMap(), view: MessageView = MessageView.Chat) =
        SimpleMessage(fromList(key, placeholders), view)

    fun sendMessage(sender: CommandSender, key: String, placeholders: Map<String, String> = emptyMap(), view: MessageView = MessageView.Chat) =
        getMessage(key, placeholders, view).send(sender)

    fun sendMessageList(sender: CommandSender, key: String, placeholders: Map<String, String> = emptyMap(), view: MessageView = MessageView.Chat) =
        getMessageList(key, placeholders, view).send(sender)

    private fun replacePlaceholders(message: String, placeholders: Map<String, String>): String =
        placeholders.entries.fold(message) { acc, (k, v) -> acc.replace("{$k}", v) }
}
