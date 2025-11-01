package com.github.grassproject.folra.util.permission

import org.bukkit.Bukkit
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault

// 추후 생각
data class PermissionData(
    val node: String,
    val default: PermissionDefault = PermissionDefault.OP
) {
    fun register() {
        if (Bukkit.getPluginManager().getPermission(node) == null) {
            Bukkit.getPluginManager().addPermission(Permission(node, default))
        }
    }
}