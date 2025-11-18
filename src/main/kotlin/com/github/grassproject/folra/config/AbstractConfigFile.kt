package com.github.grassproject.folra.config

import com.github.grassproject.folra.api.FolraPlugin
import java.io.File

abstract class AbstractConfigFile<T>(
    protected val plugin: FolraPlugin,
    protected val name: String
) {
    open val file: File = File(plugin.dataFolder, name)
    protected abstract var configuration: T

    init {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            try { plugin.saveResource(name, false) }
            catch (_: IllegalArgumentException) { file.createNewFile() }
        }
    }

    abstract fun load(): T
    abstract fun save()
    abstract fun write(path: String, value: Any)
    abstract fun remove(path: String)
    abstract fun isEmpty(): Boolean

    fun reload() {
        save()
        load()
    }

    fun update(path: String, value: Any) {
        write(path, value)
        save()
    }

    val config: T
        get() = configuration

    fun getContent(): String = file.readText()

    fun exists(): Boolean = file.exists()
    fun delete(): Boolean = file.delete()

    fun backup(suffix: String = ".bak"): Boolean {
        return try {
            file.copyTo(File(file.parentFile, "$name$suffix"), overwrite = true)
            true
        } catch (_: Exception) { false }
    }

}