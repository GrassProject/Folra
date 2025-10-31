package com.github.grassproject.folra

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.test.TestPlugin

class Folra : FolraPlugin() {

    companion object {
        val INSTANCE: FolraPlugin
            get() {
                return FolraPlugin.INSTANCE as Folra
            }

    }

    override fun load() {
        FolraPlugin.INSTANCE = this
    }

    override fun enable() {
        TestPlugin()
        info("FolraPlugin: enable() called")
    }

    override fun disable() {
        info("FolraPlugin: disable() called")
    }

}