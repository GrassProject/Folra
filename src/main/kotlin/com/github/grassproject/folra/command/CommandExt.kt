package com.github.grassproject.folra.command

import com.github.grassproject.folra.api.FolraPlugin
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault

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

fun Command.withPermission(permission: Permission) {
    this.permission = permission.name

    val pluginManager = Bukkit.getPluginManager()
    if (pluginManager.getPermission(permission.name) == null) {
        pluginManager.addPermission(permission)
    }
}

fun Command.withPermission(permission: String, default: PermissionDefault = PermissionDefault.OP) {
    this.withPermission(Permission(permission, default))
}

fun FolraBaseCommand.addSubCommand(sub: FolraBaseCommand) {
    this.subCommands.add(sub)
}

fun LiteralCommandNode<CommandSourceStack>.register(plugin: FolraPlugin) {
    plugin.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
        it.registrar().register(this)
    }
}