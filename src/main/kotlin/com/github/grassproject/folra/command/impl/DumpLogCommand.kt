package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.command.FolraBaseCommand
import com.github.grassproject.folra.util.McLogsUtil
import com.github.grassproject.folra.util.toMiniMessage
import org.bukkit.command.CommandSender

object DumpLogCommand : FolraBaseCommand(
    "dumplog", "dumplog command", listOf(), mutableListOf()
) {
    private val prefix = "<#96f19c>Folra</#96f19c><#989c99> | </#989c99>".toMiniMessage()

    override fun executeCommand(sender: CommandSender, args: Array<out String>) {
        val link = McLogsUtil.uploadLogs()
        val message = if (link != null)
            "<green>Log dump successful</green> <gray><u><click:open_url:'$link'>$link</click></u></gray>"
        else "<red>Log dump failed</red>"

        sender.sendMessage(prefix.append { message.toMiniMessage() })
    }
}