package com.github.grassproject.folra.config.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.AbstractConfigFile
import com.google.gson.Gson
import com.google.gson.JsonObject

class JsonConfigFile(
    plugin: FolraPlugin,
    name: String
) : AbstractConfigFile<JsonObject>(plugin, name) {

    private val gson = Gson()
    override var configuration: JsonObject = JsonObject()

    override fun load(): JsonObject {
        val text = file.takeIf { it.exists() }?.readText(Charsets.UTF_8) ?: "{}"
        configuration = gson.fromJson(text, JsonObject::class.java) ?: JsonObject()
        return configuration
    }

    override fun save() {
        file.writeText(gson.toJson(configuration), Charsets.UTF_8)
    }

    override fun write(path: String, value: Any) {
        val parts = path.split(".")
        var current = configuration
        for (i in 0 until parts.lastIndex) {
            val key = parts[i]
            if (!current.has(key) || !current[key].isJsonObject) {
                current.add(key, JsonObject())
            }
            current = current[key].asJsonObject
        }
        current.add(parts.last(), gson.toJsonTree(value))
        save()
    }

    override fun remove(path: String) {
        val parts = path.split(".")
        var current = configuration
        for (i in 0 until parts.lastIndex) {
            val key = parts[i]
            if (!current.has(key) || !current[key].isJsonObject) return
            current = current[key].asJsonObject
        }
        current.remove(parts.last())
        save()
    }

    override fun isEmpty(): Boolean = configuration.entrySet().isEmpty()
}
