package com.github.grassproject.folra.util.permission

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission

interface IPermission {
    val permissions: List<Permission>

    fun register() {
        permissions.forEach { perm ->
            if (Bukkit.getPluginManager().getPermission(perm.name) == null) {
                Bukkit.getPluginManager().addPermission(perm)
            }
        }
    }

    fun has(player: Player): Boolean = permissions.any { player.hasPermission(it.name) }
    fun has(sender: CommandSender): Boolean = permissions.any { sender.hasPermission(it.name) }
}