package com.github.grassproject.folra.config.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.AbstractConfigFile
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.InputStreamReader

class YamlConfigFile(
    plugin: FolraPlugin,
    name: String
) : AbstractConfigFile<FileConfiguration>(plugin, name) {

    override var configuration: FileConfiguration = YamlConfiguration()

    override fun load(): FileConfiguration {
        configuration = YamlConfiguration.loadConfiguration(file)

        plugin.getResource(name)?.use { stream ->
            val defaults = YamlConfiguration.loadConfiguration(InputStreamReader(stream, Charsets.UTF_8))
            configuration.setDefaults(defaults)
            configuration.options().copyDefaults(true)
        }

        save()
        return configuration
    }

    override fun save() {
        configuration.save(file)
    }

    override fun write(path: String, value: Any) {
        configuration.set(path, value)
    }

    override fun remove(path: String) {
        configuration.set(path, null)
    }

    override fun isEmpty(): Boolean = configuration.getKeys(false).isEmpty()
}
