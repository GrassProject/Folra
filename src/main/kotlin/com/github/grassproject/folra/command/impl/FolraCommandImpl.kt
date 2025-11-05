package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.Folra
import com.github.grassproject.folra.command.FolraBaseCommand
import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault

object FolraCommandImpl : FolraBaseCommand(
    "folra", listOf("fa"), "Folra base command",
    Permission("command.folra", PermissionDefault.OP),
    mutableMapOf(
        "reload" to ReloadCommand(Folra.INSTANCE),
        "itemconvert" to ItemConvertCommandImpl
    )
) {

    override fun onExecute(sender: CommandSender, args: Array<out String>) {
        Folra.translate.getMessage("test").send(sender)
        sender.sendMessage("a")
    }

}