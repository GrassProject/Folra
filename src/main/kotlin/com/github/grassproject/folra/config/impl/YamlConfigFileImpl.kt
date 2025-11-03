package com.github.grassproject.folra.config.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.AbstractConfigFile
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.InputStreamReader

class YamlConfigFileImpl(
    plugin: FolraPlugin,
    name: String
) : AbstractConfigFile<FileConfiguration>(plugin, name) {

    override var config: FileConfiguration = YamlConfiguration()

    override fun load(): FileConfiguration {
        config = YamlConfiguration.loadConfiguration(file)

        plugin.getResource(name)?.use { stream ->
            val defaults = YamlConfiguration.loadConfiguration(InputStreamReader(stream, Charsets.UTF_8))
            config.setDefaults(defaults)
            config.options().copyDefaults(true)
        }
        save()
        return config
    }

    override fun save() {
        config.save(file)
    }

    override fun write(path: String, value: Any) {
        config.set(path, value)
        save()
    }

    override fun remove(path: String) {
        if (config.contains(path)) {
            config.set(path, null)
            save()
        }
    }

    override fun isEmpty(): Boolean = config.getKeys(false).isEmpty()

    inline fun <reified V> getValue(path: String): V? {
        val value = getConfig().get(path) ?: return null
        return try {
            value as? V
        } catch (_: ClassCastException) {
            null
        }
    }

    fun getConfig(): FileConfiguration = config
}
