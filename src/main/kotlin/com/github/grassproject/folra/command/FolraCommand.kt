package com.github.grassproject.folra.command

import com.github.grassproject.folra.util.message.Message
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

abstract class FolraCommand(
    name: String,
    description: String,
    aliases: MutableList<String>,
    val subCommands: MutableMap<String, FolraCommandExecutor>,
    val helpMessage: () -> Message
) : Command(name, description, "/$name", aliases) {

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            helpMessage().send(sender)
            return true
        }

        val cmd = subCommands[args[0]]
        if (cmd == null) {
            helpMessage().send(sender)
            return true
        }
        cmd.execute(sender, args)
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
        if (args.size < 2) {
            return subCommands.keys.toList()
        }

        val cmd = subCommands[args[0]] ?: return listOf()
        return cmd.tabComplete(sender, args.drop(1).toTypedArray())
    }

}