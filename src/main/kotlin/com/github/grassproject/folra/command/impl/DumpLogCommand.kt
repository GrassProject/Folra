package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.command.FolraBaseCommand
import com.github.grassproject.folra.util.McLogsUtil
import com.github.grassproject.folra.util.message.FolraTranslate.Companion.toComponentWithPlaceholders
import org.bukkit.command.CommandSender

object                                                                                                                                                                                                               DumpLogCommand : FolraBaseCommand(
    "dumplog", "dumplog command", listOf(), mutableListOf()
) {
    override fun executeCommand(sender: CommandSender, args: Array<out String>) {
        val link = McLogsUtil.uploadLogs()
        val message = if (link != null)
            "<prefix> <green>Log dump successful</green> <gray><u><click:open_url:'$link'>$link</click></u></gray>"
        else "<prefix> <red>Log dump failed</red>"

        sender.sendMessage(message.toComponentWithPlaceholders())
    }
}