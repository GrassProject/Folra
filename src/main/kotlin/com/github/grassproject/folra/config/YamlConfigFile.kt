package com.github.grassproject.folra.config

import com.github.grassproject.folra.api.FolraPlugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.InputStreamReader

class YamlConfigFile(private val plugin: FolraPlugin, private val name: String) : IConfigFile<FileConfiguration> {

    private val file = File(plugin.dataFolder, name)
    private var config: FileConfiguration = YamlConfiguration.loadConfiguration(file)

    init {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            try { plugin.saveResource(name, false) }
            catch (_: IllegalArgumentException) { file.createNewFile() }
        }
        load()
    }

    override fun load(): FileConfiguration {
        config = YamlConfiguration.loadConfiguration(file)

        plugin.getResource(name)?.let { stream ->
            val defaults = YamlConfiguration.loadConfiguration(InputStreamReader(stream, Charsets.UTF_8))
            config.options().copyDefaults(true)
            config.setDefaults(defaults)
        }

        save()
        return config
    }

    override fun save() {
        config.save(file)
    }

    override fun reload() {
        save()
        load()
    }

    override fun exists(): Boolean = file.exists()
    override fun getFile(): File = file

    override fun write(path: String, value: Any) {
        config.set(path, value)
        reload()
    }

    override fun remove(path: String) {
        if (config.contains(path)) config.set(path, null)
        reload()
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

