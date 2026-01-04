package com.github.grassproject.folra.util

import com.github.grassproject.folra.api.FolraPlugin
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

fun Listener.register() {
    Bukkit.getPluginManager().registerEvents(this, FolraPlugin.INSTANCE)
}

fun Listener.unregister() {
    HandlerList.unregisterAll(this)
}

fun Event.call() {
    Bukkit.getServer().pluginManager.callEvent(this)
}

inline fun <reified T : Event> event(
    ignoreCancelled: Boolean = false,
    priority: EventPriority = EventPriority.NORMAL,
    noinline callback: (T) -> Unit
): Listener {
    val listener = object : Listener {}

    Bukkit.getPluginManager().registerEvent(
        T::class.java,
        listener,
        priority,
        { _, rawEvent ->
            if (rawEvent is T) {
                callback(rawEvent)
            }
        },
        FolraPlugin.INSTANCE,
        ignoreCancelled
    )

    return listener
}
