package com.github.grassproject.folra

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.command.impl.FolraCommandImpl
import com.github.grassproject.folra.command.register
import com.github.grassproject.folra.language.FolraTranslate
import com.github.grassproject.folra.test.TestPlugin

class Folra : FolraPlugin() {

    companion object {
        val INSTANCE: FolraPlugin
            get() {
                return FolraPlugin.INSTANCE as Folra
            }

        lateinit var translate: FolraTranslate
    }

    override fun load() {
        FolraPlugin.INSTANCE = this
        translate = FolraTranslate(this)
    }

    override fun enable() {
        TestPlugin()
        FolraPermission.register()

        FolraCommandImpl.register("folraa")

    }

    override fun disable() {
        info("FolraPlugin: disable() called")
    }

}