package com.github.grassproject.folra

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.command.FolraCommand
import com.github.grassproject.folra.command.impl.ItemConvertCommand
import com.github.grassproject.folra.command.register
import com.github.grassproject.folra.language.FolraTranslate
import com.github.grassproject.folra.test.TestPlugin
import com.github.grassproject.folra.test.command.impl.HelloCommand
import com.github.grassproject.folra.util.message.impl.EmptyMessage
import com.github.grassproject.folra.util.message.impl.SimpleMessage

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

        FolraCommand(
            "folra", "Folra base command", mutableListOf(),
            mutableMapOf(
                "itemcovert" to ItemConvertCommand
            )
        ) { EmptyMessage() }.register("folra")

        FolraCommand(
            "test", "Folra base command", mutableListOf(),
            mutableMapOf(
                "hello" to HelloCommand
            )
        ) { translate.getMessage("test") }.register("test")

    }

    override fun disable() {
        info("FolraPlugin: disable() called")
    }

}