package com.github.grassproject.folra.util.message

import com.github.grassproject.folra.util.message.impl.view.MessageView
import org.bukkit.command.CommandSender

class SimpleMessage(
    override val lines: List<String>,
    private val view: MessageView
) : MessageContent, MessageSender {
    override fun replace(from: String, to: String): MessageContent =
        SimpleMessage(lines.map { it.replace(from, to) }, view)

    override fun replace(updater: (String) -> String): MessageContent =
        SimpleMessage(lines.map(updater), view)

    override fun send(sender: CommandSender, message: MessageContent, view: MessageView) {
        message.lines.forEach { sender.sendMessage(it) }
    }

    override fun broadcast(message: MessageContent, view: MessageView) {
        sender.server.onlinePlayers.forEach { send(it, message, view) }
    }
}
