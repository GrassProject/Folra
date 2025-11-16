package com.github.grassproject.folra.api

import org.bukkit.plugin.Plugin

interface IFolraPlugin : Plugin {

    fun load() {}
    fun enable() {}
    fun disable() {}

}