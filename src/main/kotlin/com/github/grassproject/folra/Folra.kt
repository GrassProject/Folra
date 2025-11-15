package com.github.grassproject.folra

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.util.message.FolraTranslate

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

    }

}