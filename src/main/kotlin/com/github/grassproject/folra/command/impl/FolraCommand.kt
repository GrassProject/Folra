package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.command.FolraBaseCommand
import org.bukkit.command.CommandSender

class FolraCommand : FolraBaseCommand(
    "folra", "Folra base command", listOf(), mutableListOf(ItemConvertCommand)
) {
    override fun executeCommand(sender: CommandSender, args: Array<out String>) {
        sender.sendMessage("a")
    }

}