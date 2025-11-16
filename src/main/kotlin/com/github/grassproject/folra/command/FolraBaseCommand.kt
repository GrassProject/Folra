package com.github.grassproject.folra.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class FolraBaseCommand(
    name: String,
    description: String,
    aliases: List<String> = listOf(),
    val subCommands: MutableList<FolraBaseCommand> = mutableListOf()
) : Command(name, description, "/$name", aliases) {

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()) {
            val subName = args[0]
            val sub = subCommands.firstOrNull { it.name.equals(subName, ignoreCase = true) }
            if (sub != null) {
                return sub.execute(sender, commandLabel, args.drop(1).toTypedArray())
            }
        }
        executeCommand(sender, args)
        return true
    }

    abstract fun executeCommand(sender: CommandSender, args: Array<out String>)

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
        if (args.isEmpty()) return emptyList()

        val firstArg = args[0]
        return if (args.size == 1) {
            subCommands.map { it.name }.filter { it.startsWith(firstArg, ignoreCase = true) }
        } else {
            subCommands.firstOrNull { it.name.equals(firstArg, ignoreCase = true) }
                ?.tabComplete(sender, alias, args.drop(1).toTypedArray())
                ?: emptyList()
        }
    }

    open fun onTabComplete(sender: CommandSender, args: Array<out String>): List<String> = listOf()
}
