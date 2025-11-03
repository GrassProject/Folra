package com.github.grassproject.folra.config

import com.github.grassproject.folra.api.FolraPlugin
import com.google.gson.Gson
import java.io.File

abstract class AbstractConfigFile<T>(
    protected val plugin: FolraPlugin,
    protected val name: String
) {
    open val file: File = File(plugin.dataFolder, name)
    protected abstract var config: T

    init {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            try { plugin.saveResource(name, false) }
            catch (_: IllegalArgumentException) { file.createNewFile() }
        }
        load()
    }

    abstract fun load(): T
    abstract fun save()
    abstract fun write(path: String, value: Any)
    abstract fun remove(path: String)
    abstract fun isEmpty(): Boolean

    fun getConfig(): T = config

    fun update(path: String, value: Any) {
        write(path, value)
        save()
    }

    open fun reload() {
        save()
        load()
    }

    fun getContent(): String = file.readText()
    fun exists(): Boolean = file.exists()
    fun delete(): Boolean = file.delete()

    fun backup(suffix: String = ".bak"): Boolean {
        return try {
            file.copyTo(File(file.parentFile, "$name$suffix"), overwrite = true)
            true
        } catch (_: Exception) { false }
    }

    inline fun <reified D : Any> toData(): D? {
        return try {
            Gson().fromJson(file.readText(), D::class.java)
        } catch (_: Exception) { null }
    }

}
