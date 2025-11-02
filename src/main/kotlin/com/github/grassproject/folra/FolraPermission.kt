package com.github.grassproject.folra

import com.github.grassproject.folra.util.permission.IPermission
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault

enum class FolraPermission(
    node: String,
    default: PermissionDefault = PermissionDefault.OP
) : IPermission {

    FOLRA_ADMIN("folra.admin");

    private val perm: Permission = Permission(node, default)

    override val permissions: List<Permission>
        get() = listOf(perm)

    companion object {
        fun register() {
            entries.forEach { it.register() }
        }
    }
}