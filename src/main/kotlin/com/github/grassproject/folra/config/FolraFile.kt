package com.github.grassproject.folra.config

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.impl.*

@Deprecated("fxxk file")
object FolraFile {

    fun create(plugin: FolraPlugin, name: String, type: ConfigFileType): AbstractConfigFile<*> {
        val file: AbstractConfigFile<*> = when (type) {
            ConfigFileType.JSON -> JsonConfigFileImpl(plugin, "$name.json")
            ConfigFileType.TOML -> TomlConfigFileImpl(plugin, "$name.toml")
            ConfigFileType.YAML -> YamlConfigFileImpl(plugin, "$name.yml")
        }
        return file
    }

}