package com.github.grassproject.folra.test

import com.github.grassproject.folra.Folra
import com.github.grassproject.folra.api.event.register
import com.github.grassproject.folra.test.listener.PlayerListener
import com.github.grassproject.folra.util.ConfigFile

class TestPlugin {
    init {
        PlayerListener().register()
        // FolraCommandImpl().register("folra")

        ConfigFile(Folra.INSTANCE, "config.yml").load()
        ConfigFile(Folra.INSTANCE, "command.yml").load()
    }
}