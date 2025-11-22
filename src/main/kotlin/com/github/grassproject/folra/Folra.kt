package com.github.grassproject.folra

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.command.impl.FolraCommand
import com.github.grassproject.folra.command.register
import com.github.grassproject.folra.command.withPermission
import com.github.grassproject.folra.config.impl.YamlConfigFile
import com.github.grassproject.folra.database.DatabaseManager
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
    }

    override fun enable() {
        YamlConfigFile(this, "config.yml")
        TRANSLATE.init()

        FolraCommand(this).apply {
            withPermission("folra.admin")
        }.register("folra")

        DatabaseManager(this).loadDatabase()

    }

}