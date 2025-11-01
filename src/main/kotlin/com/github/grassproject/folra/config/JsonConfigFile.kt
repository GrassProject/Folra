package com.github.grassproject.folra.config

import com.github.grassproject.folra.api.FolraPlugin
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.File

class JsonConfigFile(private val plugin: FolraPlugin, private val name: String) : IConfigFile<JsonObject> {

    private val file = File(plugin.dataFolder, name)
    private var json: JsonObject = JsonObject()

    init {
        if (!file.exists()) plugin.saveResource(name, false)
        load()
    }

    override fun load(): JsonObject {
        val text = file.readText(Charsets.UTF_8)
        json = Gson().fromJson(text, JsonObject::class.java) ?: JsonObject()
        return json
    }

    override fun save() {
        file.writeText(Gson().toJson(json), Charsets.UTF_8)
    }

    override fun reload() {
        save()
        load()
    }

    override fun exists(): Boolean = file.exists()

    override fun getFile(): File = file

    override fun write(path: String, value: Any) {
        val parts = path.split(".")
        var current = json
        for (i in 0 until parts.size - 1) {
            val key = parts[i]
            if (!current.has(key) || !current[key].isJsonObject) {
                current.add(key, JsonObject())
            }
            current = current[key].asJsonObject
        }
        current.add(parts.last(), Gson().toJsonTree(value))
        reload()
    }

    override fun remove(path: String) {
        val parts = path.split(".")
        var current: JsonObject = json
        for (i in 0 until parts.size - 1) {
            val key = parts[i]
            if (!current.has(key) || !current[key].isJsonObject) return
            current = current[key].asJsonObject
        }
        current.remove(parts.last())
        reload()
    }

    override fun isEmpty(): Boolean = json.entrySet().isEmpty()

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

    fun getJson(): JsonObject = json
}
