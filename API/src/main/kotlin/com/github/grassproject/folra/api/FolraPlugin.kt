package com.github.grassproject.folra.api

import com.github.grassproject.folra.api.event.FolraPluginRegisterEvent
import com.github.grassproject.folra.api.event.FolraPluginUnregisterEvent
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
        FolraPluginRegisterEvent(this).callEvent()
        enable()

        info("Registered plugin $name")
    }

    override fun onDisable() {
        PLUGINS.remove(this)
        FolraPluginUnregisterEvent(this).callEvent()
        disable()
    }

}