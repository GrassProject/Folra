package com.github.grassproject.folra.util.message.impl

import com.github.grassproject.folra.util.message.Message
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class SimpleMessage(
    override val messages: Collection<String>
) : Message {

    override fun replace(updater: (String) -> String): Message {
        return SimpleMessage(messages.map { updater(it) })
    }

    override fun replace(from: String, to: String): Message {
        return replace { it.replace(from, to) }
    }

    override fun send(sender: CommandSender) {
        messages.forEach { sender.sendMessage(it) }
    }

    override fun broadcast() {
        messages.forEach { Bukkit.broadcastMessage(it) }
    }
}