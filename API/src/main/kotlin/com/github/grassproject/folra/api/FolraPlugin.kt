package com.github.grassproject.folra.api

import org.bukkit.plugin.java.JavaPlugin

abstract class FolraPlugin : JavaPlugin(), IFolraPlugin {

    companion object {
        lateinit var INSTANCE: FolraPlugin

        val PLUGINS: MutableSet<FolraPlugin> = mutableSetOf()
    }

    override fun onLoad() {
        load()
    }

    override fun onEnable() {
        PLUGINS.add(this)
        info("Registered plugin $name")
        enable()
    }

    override fun onDisable() {
        PLUGINS.remove(this)
        disable()
    }
}
