package com.github.grassproject.folra.test

import com.github.grassproject.folra.Folra
import com.github.grassproject.folra.api.event.register
import com.github.grassproject.folra.config.YamlConfigFile
import com.github.grassproject.folra.test.listener.PlayerListener

class TestPlugin {
    init {
        PlayerListener().register()
        // FolraCommandImpl().register("folra")

        YamlConfigFile(Folra.INSTANCE, "config.yml").load()
        YamlConfigFile(Folra.INSTANCE, "command.yml").load()
    }
}