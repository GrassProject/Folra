package com.github.grassproject.folra.config

import com.github.grassproject.folra.api.FolraPlugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.InputStreamReader

class YamlConfigFile(private val plugin: FolraPlugin, private val name: String) : IConfigFile<FileConfiguration> {

    private val file = File(plugin.dataFolder, name)
    private var config: FileConfiguration = YamlConfiguration.loadConfiguration(file)

    override fun load(): FileConfiguration {
        file.parentFile?.mkdirs()
        if (!file.exists()) {
            try { plugin.saveResource(name, false) }
            catch (_: IllegalArgumentException) { file.createNewFile() }
        }

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

    override fun exists(): Boolean = file.exists()
    override fun getFile(): File = file
    fun getConfig(): FileConfiguration = config
}
