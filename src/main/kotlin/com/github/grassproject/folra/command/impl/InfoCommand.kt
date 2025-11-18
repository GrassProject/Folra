package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.Folra
import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.command.FolraBaseCommand
import org.bukkit.command.CommandSender

class InfoCommand(val plugin: FolraPlugin) : FolraBaseCommand(
    "info", "server info command", listOf(), mutableListOf()
) {
    override fun executeCommand(sender: CommandSender, args: Array<out String>) {
        val message = Folra.TRANSLATE.componentList(
            "command.folra.info",
            mapOf(
                "server" to plugin.server.name,
                "version" to plugin.server.version,
                "nms" to plugin.server.bukkitVersion,
                "name" to plugin.name,
                "plugin-version" to plugin.pluginMeta.version,
                "list" to FolraPlugin.PLUGINS.joinToString(", ") { it.name }
            )
        )
        message.forEach { sender.sendMessage(it) }
    }
}