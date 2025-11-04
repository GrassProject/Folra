package com.github.grassproject.folra.util.message.impl

import com.github.grassproject.folra.util.message.Message
import com.github.grassproject.folra.util.message.impl.view.MessageView
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class SimpleMessage(
    override val messages: List<String>,
    override val view: MessageView = MessageView.Chat
) : Message {

    override fun replace(from: String, to: String): Message =
        SimpleMessage(messages.map { it.replace(from, to) }, view)

    override fun replace(updater: (String) -> String): Message =
        SimpleMessage(messages.map(updater), view)

    override fun send(sender: CommandSender) {
        val components = messages.map { Component.text(it) }
        view.send(sender, components)
    }

    override fun broadcast() {
        Bukkit.getOnlinePlayers().forEach { send(it) }
    }
}