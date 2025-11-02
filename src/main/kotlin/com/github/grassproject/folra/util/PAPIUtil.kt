package com.github.grassproject.folra.util

import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

object PAPIUtil {

    fun registerExtension(
        author: String,
        identifier: String,
        persist: Boolean = true, // false
        canRegister: Boolean = true,
        onRequest: (player: OfflinePlayer, params: String) -> String,
    ) {
        if (PluginUtil.isPresent("PlaceholderAPI")) return

        val expansion = object : PlaceholderExpansion() {
            override fun getIdentifier(): String = identifier
            override fun getAuthor(): String = author
            override fun getVersion(): String = "1.0.0"
            override fun canRegister(): Boolean = canRegister
            override fun persist(): Boolean = persist
            override fun onRequest(player: OfflinePlayer, params: String): String =
                onRequest(player, params)
        }
        expansion.register()
    }

}

fun String.replacePAPIPlaceholders(player: Player): String {
    if (!PluginUtil.isPresent("PlaceholderAPI")) return this
    return PlaceholderAPI.setPlaceholders(player, this)
}