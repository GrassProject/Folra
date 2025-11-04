package com.github.grassproject.folra.util.message

import com.github.grassproject.folra.util.message.impl.EmptyMessage
import com.github.grassproject.folra.util.message.impl.view.MessageView
import org.bukkit.command.CommandSender

interface Message {
    val messages: List<String>
    val view: MessageView

    fun replace(from: String, to: String): Message
    fun replace(updater: (String) -> String): Message

    fun send(sender: CommandSender)
    fun broadcast()

    companion object {
        val EMPTY: Message = EmptyMessage()
    }
}