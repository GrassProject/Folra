package com.github.grassproject.folra.util.message

import org.bukkit.command.CommandSender

interface Message {
    val messages: Collection<String>

    fun replace(updater: (String) -> String): Message
    fun replace(from: String, to: String): Message

    fun send(sender: CommandSender)

    fun broadcast()

}