package com.github.grassproject.folra.config.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.AbstractConfigFile
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

class JsonConfigFileImpl(
    plugin: FolraPlugin,
    name: String
) : AbstractConfigFile<JsonObject>(plugin, name) {

    override var config: JsonObject = JsonObject()

    override fun load(): JsonObject {
        val text = file.takeIf { it.exists() }?.readText(Charsets.UTF_8) ?: "{}"
        config = Gson().fromJson(text, JsonObject::class.java) ?: JsonObject()
        return config
    }

    override fun save() {
        file.writeText(Gson().toJson(config), Charsets.UTF_8)
    }

    override fun write(path: String, value: Any) {
        val parts = path.split(".")
        var current = config
        for (i in 0 until parts.lastIndex) {
            val key = parts[i]
            if (!current.has(key) || !current[key].isJsonObject) {
                current.add(key, JsonObject())
            }
            current = current[key].asJsonObject
        }
        current.add(parts.last(), Gson().toJsonTree(value))
        save()
    }

    override fun remove(path: String) {
        val parts = path.split(".")
        var current = config
        for (i in 0 until parts.lastIndex) {
            val key = parts[i]
            if (!current.has(key) || !current[key].isJsonObject) return
            current = current[key].asJsonObject
        }
        current.remove(parts.last())
        save()
    }

    override fun isEmpty(): Boolean = config.entrySet().isEmpty()

    inline fun <reified V> getValue(path: String): V? {
        val parts = path.split(".")
        var current: JsonElement = getJson()
        for (part in parts) {
            if (!current.isJsonObject || !current.asJsonObject.has(part)) return null
            current = current.asJsonObject.get(part)
        }
        return try {
            Gson().fromJson(current, V::class.java)
        } catch (_: Exception) {
            null
        }
    }

    fun getJson(): JsonObject = config

    fun <D : Any> toData(dataClass: Class<D>): D? {
        return file.reader().use { reader ->
            Gson().fromJson(reader, dataClass)
        }
    }
}
