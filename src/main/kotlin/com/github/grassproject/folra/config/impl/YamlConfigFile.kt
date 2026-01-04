package com.github.grassproject.folra.config.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.AbstractConfigFile
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class YamlConfigFile(
    plugin: FolraPlugin,
    name: String
) : AbstractConfigFile<FileConfiguration>(plugin, name) {
    override var configuration: FileConfiguration = YamlConfiguration()

    override fun load(): FileConfiguration {
        configuration = YamlConfiguration.loadConfiguration(file)

        plugin.getResource(name)?.let { stream ->
            val defaults = YamlConfiguration.loadConfiguration(InputStreamReader(stream, StandardCharsets.UTF_8))
            configuration.setDefaults(defaults)
            configuration.options().copyDefaults(true)
        }

        return configuration
    }

    override fun save() {
        try {
            configuration.save(file)
        } catch (e: Exception) {
            plugin.logger.severe("Could not save config to $file: ${e.message}")
        }
    }

    override fun write(path: String, value: Any?, saveAfter: Boolean) {
        configuration.set(path, value)
        if (saveAfter) save()
    }

    override fun remove(path: String, saveAfter: Boolean) {
        configuration.set(path, null)
        if (saveAfter) save()
    }

    override fun isEmpty(): Boolean = configuration.getKeys(false).isEmpty()
}