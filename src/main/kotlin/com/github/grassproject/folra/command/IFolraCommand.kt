package com.github.grassproject.folra.command

import org.bukkit.command.CommandSender

interface IFolraCommand {
    val permission: String?
        get() = null

    fun execute(sender: CommandSender, args: Array<out String>)
    fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> = listOf()
}