package com.github.grassproject.folra.command

import com.github.grassproject.folra.util.message.Message
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permission

abstract class FolraBaseCommand(
    name: String,
    aliases: List<String> = emptyList(),
    description: String = "",
    permission: Permission? = null,
    val subCommands: MutableMap<String, IFolraCommand> = mutableMapOf(),
    val helpMessage: () -> Message = { Message.EMPTY }
) : Command(name, description, "/$name", aliases) {

    init {
        permission?.let { perm ->
            this.permission = perm.name
            val pm = Bukkit.getPluginManager()
            if (pm.getPermission(perm.name) == null) pm.addPermission(perm)
        }
    }

    abstract fun onExecute(sender: CommandSender, args: Array<out String>)
    open fun onTabComplete(sender: CommandSender, args: Array<out String>): List<String> = listOf()

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        val subCommand = args.firstOrNull()?.lowercase()?.let(subCommands::get)

        if (subCommand == null) {
            onExecute(sender, args)
            return true
        }

        if (!subCommand.permission.isNullOrEmpty() && !sender.hasPermission(subCommand.permission!!)) {
            helpMessage().send(sender)
            return true
        }

        subCommand.execute(sender, args.drop(1).toTypedArray())
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
        if (args.size <= 1) {
            val prefix = args.getOrNull(0)?.lowercase().orEmpty()
            return subCommands.keys.filter { it.startsWith(prefix) }
        }

        val subCommand = subCommands[args[0].lowercase()] ?: return emptyList()

        if (!subCommand.permission.isNullOrEmpty() && !sender.hasPermission(subCommand.permission!!)) {
            return emptyList()
        }

        return subCommand.tabComplete(sender, args.drop(1).toTypedArray())
    }
}
