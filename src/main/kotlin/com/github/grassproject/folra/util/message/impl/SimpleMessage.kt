package com.github.grassproject.folra.util.message.impl

import com.github.grassproject.folra.util.message.Message
import com.github.grassproject.folra.util.message.impl.view.MessageView
import com.github.grassproject.folra.util.toMiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class SimpleMessage(
    override var messages: Collection<String>,
    private val view: MessageView = MessageView.Chat
) : Message {

    constructor(message: String) : this(listOf(message))

    override fun replace(updater: (String) -> String): Message =
        SimpleMessage(messages.map(updater), view)

    override fun replace(from: String, to: String): Message =
        SimpleMessage(messages.map { it.replace(from, to) }, view)

    override fun send(sender: CommandSender) {
        view.send(sender, messages.map { it.toMiniMessage() })
    }

    override fun broadcast() {
        if (messages.isEmpty() || messages.all { it.isEmpty() }) return
        Bukkit.getOnlinePlayers().forEach { view.send(it, messages.map { msg -> msg.toMiniMessage() }) }
    }
}
