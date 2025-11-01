package com.github.grassproject.folra.config

import com.github.grassproject.folra.api.FolraPlugin
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.File

class JsonConfigFile(private val plugin: FolraPlugin, private val name: String) : IConfigFile<JsonObject> {

    private val file = File(plugin.dataFolder, name)
    private var json: JsonObject = JsonObject()

    init {
        if (!file.exists()) plugin.saveResource(name, false)
    }

    override fun load(): JsonObject {
        val text = file.readText(Charsets.UTF_8)
        json = Gson().fromJson(text, JsonObject::class.java)
        return json
    }

    override fun save() {
        file.writeText(Gson().toJson(json), Charsets.UTF_8)
    }

    override fun exists(): Boolean = file.exists()
    override fun getFile(): File = file
    fun getJson(): JsonObject = json
}
