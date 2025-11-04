package com.github.grassproject.folra.util.message.impl

import com.github.grassproject.folra.util.message.Message
import com.github.grassproject.folra.util.message.impl.view.MessageView
import org.bukkit.command.CommandSender

class EmptyMessage : Message {
    override val messages: List<String> = emptyList()
    override val view: MessageView = MessageView.Chat

    override fun replace(from: String, to: String) = this
    override fun replace(updater: (String) -> String) = this

    override fun send(sender: CommandSender) {}
    override fun broadcast() {}
}