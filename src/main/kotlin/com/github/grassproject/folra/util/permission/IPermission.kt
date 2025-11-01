package com.github.grassproject.folra.util.permission

import org.bukkit.Bukkit
import org.bukkit.permissions.Permission

// 보류 enum 변환 예정
interface IPermission {

    val permissions: List<Permission>

    fun register() = IPermission.register(permissions)

    companion object {
        fun register(permissions: List<Permission>) {
            permissions.forEach { perm ->
                if (Bukkit.getPluginManager().getPermission(perm.name) == null) {
                    Bukkit.getPluginManager().addPermission(perm)
                }
            }
        }
    }
}