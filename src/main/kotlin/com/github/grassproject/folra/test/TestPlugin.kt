package com.github.grassproject.folra.test

import com.github.grassproject.folra.Folra
import com.github.grassproject.folra.api.event.register
import com.github.grassproject.folra.config.YamlConfigFile
import com.github.grassproject.folra.test.listener.PlayerListener

class TestPlugin {
    init {
        PlayerListener().register()
        // FolraCommandImpl().register("folra")

        val config = YamlConfigFile(Folra.INSTANCE, "test.yml")
        config.load()
        config.write("test", false)
        println("aaaaaaaaa ${config.getValue<Boolean>("test")}")
        println("aaaaaaaaa ${config.getValue<Boolean>("test")}")
        println("aaaaaaaaa ${config.getValue<Boolean>("test")}")
        YamlConfigFile(Folra.INSTANCE, "command.yml").load()
    }
}