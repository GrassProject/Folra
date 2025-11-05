package com.github.grassproject.folra.util.message.impl

import com.github.grassproject.folra.util.message.Message
import com.github.grassproject.folra.util.toMiniMessage
import com.github.grassproject.folra.util.message.impl.view.MessageView
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class SimpleMessage(
    override val messages: List<String>,
    override val view: MessageView = MessageView.Chat
) : Message {

    private val components by lazy { messages.map { it.toMiniMessage() } }

    override fun send(sender: CommandSender) {
        view.send(sender, components)
    }

    override fun broadcast() {
        Bukkit.getOnlinePlayers().forEach(::send)
    }

    override fun replace(from: String, to: String): Message =
        SimpleMessage(messages.map { it.replace("{$from}", to) }, view)

    override fun replace(updater: (String) -> String): Message =
        SimpleMessage(messages.map(updater), view)
}
