package com.github.grassproject.folra.test.command.impl

import com.github.grassproject.folra.command.FolraCommandExecutor
import org.bukkit.command.CommandSender

object HelloCommand : FolraCommandExecutor {
    override fun execute(sender: CommandSender, args: Array<out String>) {
        sender.sendMessage("Hello")
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return listOf()
    }
}