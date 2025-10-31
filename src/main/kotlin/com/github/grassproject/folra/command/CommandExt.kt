package com.github.grassproject.folra.command

import org.bukkit.Bukkit
import org.bukkit.command.Command

fun Command.register(plugin: String) {
    unregister()
    Bukkit.getServer().commandMap.register(plugin, this)
}

fun Command.unregister() {
    val knownCommands = Bukkit.getServer().commandMap.knownCommands
    knownCommands.remove(name)
    aliases.forEach { alias ->
        knownCommands[alias]?.let {
            if (it.name.equals(name, ignoreCase = true)) knownCommands.remove(alias)
        }
    }
}