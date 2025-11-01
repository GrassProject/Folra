package com.github.grassproject.folra.test.listener

import com.github.grassproject.folra.api.FolraPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener : Listener {

    @EventHandler
    fun PlayerJoinEvent.on() {
        FolraPlugin.PLUGINS.forEach { player.sendMessage(it.toString()) }
    }
}