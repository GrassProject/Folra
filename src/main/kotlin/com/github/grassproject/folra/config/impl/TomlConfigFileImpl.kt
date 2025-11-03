package com.github.grassproject.folra.config.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.AbstractConfigFile

class TomlConfigFileImpl(
    plugin: FolraPlugin,
    name: String
) : AbstractConfigFile<MutableMap<String, MutableMap<String, String>>>(plugin, name) {

    override var configuration: MutableMap<String, MutableMap<String, String>> = mutableMapOf()

    override fun load(): MutableMap<String, MutableMap<String, String>> {
        configuration.clear()
        var currentSection: String? = null

        file.forEachLine { line ->
            val trimmed = line.trim()
            if (trimmed.isEmpty() || trimmed.startsWith("#")) return@forEachLine

            when {
                trimmed.startsWith("[") && trimmed.endsWith("]") -> {
                    currentSection = trimmed.removeSurrounding("[", "]").trim()
                    configuration.putIfAbsent(currentSection, mutableMapOf())
                }
                "=" in trimmed -> parseKeyValue(trimmed)?.let { (key, value) ->
                    val targetMap = if (currentSection != null) {
                        configuration.getOrPut(currentSection) { mutableMapOf() }
                    } else {
                        configuration.getOrPut("") { mutableMapOf() }
                    }
                    targetMap[key] = value
                }
            }
        }
        return configuration
    }

    private fun parseKeyValue(line: String): Pair<String, String>? {
        val parts = line.split("=", limit = 2).map { it.trim() }
        if (parts.size != 2) return null
        return parts[0] to parts[1].removeSurrounding("\"").removeSuffix(",")
    }

    override fun save() {
        val builder = StringBuilder()

        configuration[""]?.forEach { (key, value) ->
            builder.appendLine("$key = \"$value\"")
        }

        configuration.filterKeys { it.isNotEmpty() }.forEach { (section, entries) ->
            builder.appendLine()
            builder.appendLine("[$section]")
            entries.forEach { (key, value) ->
                builder.appendLine("$key = \"$value\"")
            }
        }

        file.writeText(builder.toString().trim())
    }

    override fun write(path: String, value: Any) {
        val (section, key) = parsePath(path)
        val targetMap = if (section.isEmpty()) {
            configuration.getOrPut("") { mutableMapOf() }
        } else {
            configuration.getOrPut(section) { mutableMapOf() }
        }
        targetMap[key] = value.toString()
    }

    override fun remove(path: String) {
        val (section, key) = parsePath(path)
        configuration[section]?.remove(key)
    }

    private fun parsePath(path: String): Pair<String, String> {
        val parts = path.split(".", limit = 2)
        return parts.getOrElse(0) { "" } to parts.getOrElse(1) { "" }
    }

    override fun isEmpty(): Boolean = configuration.isEmpty()
}
