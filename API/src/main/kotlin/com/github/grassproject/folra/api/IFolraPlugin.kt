package com.github.grassproject.folra.api

import org.bukkit.plugin.Plugin

interface IFolraPlugin : Plugin {

    fun load() {}
    fun enable() {}
    fun disable() {}

    fun info(msg: String) = logger.info(msg)
    fun warn(msg: String) = logger.warning(msg)
    fun severe(msg: String) = logger.severe(msg)
    fun debug(msg: String) = info("[DEBUG] $msg")

}
