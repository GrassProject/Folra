package com.github.grassproject.folra.test

import com.github.grassproject.folra.Folra
import com.github.grassproject.folra.api.event.register
import com.github.grassproject.folra.config.impl.JsonConfigFileImpl
import com.github.grassproject.folra.config.impl.YamlConfigFileImpl
import com.github.grassproject.folra.test.listener.PlayerListener

class TestPlugin {
    init {
        PlayerListener().register()
        // FolraCommandImpl().register("folra")

        val config = YamlConfigFileImpl(Folra.INSTANCE, "test.yml")
        config.load()
        config.write("test", false)
        println("aaaaaaaaa")
        println("aaaaaaaaa ${config.getConfig()}")
        println("aaaaaaaaa ${config.getValue<Boolean>("test")}")
        YamlConfigFileImpl(Folra.INSTANCE, "command.yml").load()
        JsonConfigFileImpl(Folra.INSTANCE, "command.json").load()
    }
}