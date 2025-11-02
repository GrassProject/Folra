package com.github.grassproject.folra.util

import com.github.grassproject.folra.api.FolraPlugin
import org.bukkit.Bukkit

object PluginUtil {

    private val pluginManager get() = Bukkit.getPluginManager()

    fun isEnabled(plugin: String): Boolean = pluginManager.isPluginEnabled(plugin)

    fun isPresent(plugin: String): Boolean = pluginManager.getPlugin(plugin) != null

    fun enablePlugin(plugin: String) {
        pluginManager.getPlugin(plugin)?.let { pluginManager.enablePlugin(it) }
    }

    fun disablePlugin(plugin: String) {
        pluginManager.getPlugin(plugin)?.let { pluginManager.disablePlugin(it) }
    }

    fun asynchronously(instance: FolraPlugin, action: () -> Unit) {
        Bukkit.getScheduler().runTaskAsynchronously(instance, Runnable(action))
    }

    fun runTask(instance: FolraPlugin, action: () -> Unit) {
        Bukkit.getScheduler().runTask(instance, Runnable(action))
    }
}
