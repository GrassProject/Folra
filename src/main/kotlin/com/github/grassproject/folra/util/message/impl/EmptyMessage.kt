package com.github.grassproject.folra.util.message.impl

import com.github.grassproject.folra.util.message.Message
import org.bukkit.command.CommandSender

class EmptyMessage : Message {
    override val messages: Collection<String> = emptyList()

    override fun replace(updater: (String) -> String): Message {
        return this
    }

    override fun replace(from: String, to: String): Message {
        return this
    }

    override fun send(sender: CommandSender) {
        return
    }

    override fun broadcast() {
        return
    }
}