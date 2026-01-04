package com.github.grassproject.folra.config.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.AbstractConfigFile
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import java.nio.charset.StandardCharsets

class JsonConfigFile(
    plugin: FolraPlugin,
    name: String
) : AbstractConfigFile<JsonObject>(plugin, name) {

    companion object {
        private val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
    }

    override var configuration: JsonObject = JsonObject()

    override fun load(): JsonObject {
        return try {
            if (!file.exists()) {
                configuration = JsonObject()
                return configuration
            }
            val reader = file.reader(StandardCharsets.UTF_8)
            configuration = JsonParser.parseReader(reader).asJsonObject
            reader.close()
            configuration
        } catch (e: Exception) {
            configuration = JsonObject()
            configuration
        }
    }

    override fun save() {
        try {
            file.writeText(GSON.toJson(configuration), StandardCharsets.UTF_8)
        } catch (e: Exception) {
            plugin.logger.severe("Could not save JSON config to $file: ${e.message}")
        }
    }

    override fun write(path: String, value: Any?, saveAfter: Boolean) {
        val parts = path.split(".")
        var current = configuration

        for (i in 0 until parts.lastIndex) {
            val key = parts[i]
            val element = current.get(key)
            if (element == null || !element.isJsonObject) {
                val newObj = JsonObject()
                current.add(key, newObj)
                current = newObj
            } else {
                current = element.asJsonObject
            }
        }

        current.add(parts.last(), GSON.toJsonTree(value))
        if (saveAfter) save()
    }

    override fun remove(path: String, saveAfter: Boolean) {
        val parts = path.split(".")
        var current = configuration

        for (i in 0 until parts.lastIndex) {
            val key = parts[i]
            val element = current.get(key) ?: return
            if (!element.isJsonObject) return
            current = element.asJsonObject
        }

        current.remove(parts.last())
        if (saveAfter) save()
    }

    override fun isEmpty(): Boolean = configuration.size() == 0
}