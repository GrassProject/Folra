package com.github.grassproject.folra.api.event

import com.github.grassproject.folra.api.FolraPlugin
import org.bukkit.Bukkit
import org.bukkit.event.Listener

fun Listener.register() {
    Bukkit.getPluginManager().registerEvents(this, FolraPlugin.INSTANCE)
}