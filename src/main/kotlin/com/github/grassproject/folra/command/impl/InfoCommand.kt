package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.command.FolraBaseCommand
import org.bukkit.command.CommandSender

class InfoCommand : FolraBaseCommand(
    "info", "server info command", listOf(), mutableListOf()
) {
    override fun executeCommand(sender: CommandSender, args: Array<out String>) {
        TODO("Not yet implemented")
    }
}