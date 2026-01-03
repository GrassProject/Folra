package com.github.grassproject.folra.util

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*

fun UUID.toOfflinePlayer(): OfflinePlayer {
    return Bukkit.getOfflinePlayer(this)
}

fun String.toPlayer(): OfflinePlayer {
    return try {
        Bukkit.getOfflinePlayer(UUID.fromString(this))
    } catch (e: Exception) {
        Bukkit.getOfflinePlayer(this)
    }
}

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}