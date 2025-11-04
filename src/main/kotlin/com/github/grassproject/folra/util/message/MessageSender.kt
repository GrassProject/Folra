package com.github.grassproject.folra.util.message

import com.github.grassproject.folra.util.message.impl.view.MessageView
import org.bukkit.command.CommandSender

interface MessageSender {
    fun send(sender: CommandSender, message: MessageContent, view: MessageView)
    fun broadcast(message: MessageContent, view: MessageView)
}
