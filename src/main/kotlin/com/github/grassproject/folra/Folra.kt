package com.github.grassproject.folra

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.command.impl.FolraCommand
import com.github.grassproject.folra.command.register
import com.github.grassproject.folra.command.withPermission
import com.github.grassproject.folra.util.message.FolraTranslate

class Folra : FolraPlugin() {

    companion object {
        val INSTANCE: FolraPlugin
            get() {
                return FolraPlugin.INSTANCE as Folra
            }

        lateinit var TRANSLATE: FolraTranslate
    }

    override fun load() {
        FolraPlugin.INSTANCE = this
        TRANSLATE = FolraTranslate(this)
        TRANSLATE.init()
    }

    override fun enable() {
        FolraCommand().apply {
            withPermission("folra.admin")
        }.register("folra")
    }

}