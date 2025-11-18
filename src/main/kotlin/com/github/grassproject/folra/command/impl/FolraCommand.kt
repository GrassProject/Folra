package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.command.FolraBaseCommand
import org.bukkit.command.CommandSender

class FolraCommand(val plugin: FolraPlugin) : FolraBaseCommand(
    "folra", "Folra base command",
    listOf(), mutableListOf(
        ItemConvertCommand,
        InfoCommand(plugin),
        DumpLogCommand
    )
) {
    override fun executeCommand(sender: CommandSender, args: Array<out String>) {
        sender.sendMessage("a")
    }

}