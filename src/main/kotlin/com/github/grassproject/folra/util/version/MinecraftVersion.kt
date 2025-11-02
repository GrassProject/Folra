package com.github.grassproject.folra.util.version

import com.github.grassproject.folra.api.FolraPlugin

enum class MinecraftVersion {
    V_1_21_8;

    fun isOlder(version: MinecraftVersion): Boolean {
        return this.ordinal < version.ordinal
    }

    fun isNewer(version: MinecraftVersion): Boolean {
        return this.ordinal > version.ordinal
    }

    companion object {
        fun ofVersion(plugin: FolraPlugin): MinecraftVersion? {
            val version = plugin.server.bukkitVersion.split("-")[0]
            return when(version) {
                "1.21.8" -> V_1_21_8
                else -> null
            }
        }
    }
}