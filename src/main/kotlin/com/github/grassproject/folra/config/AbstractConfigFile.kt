package com.github.grassproject.folra.config

import com.github.grassproject.folra.api.FolraPlugin
import java.io.File
import java.io.IOException

abstract class AbstractConfigFile<T>(
    protected val plugin: FolraPlugin,
    protected val name: String
) {
    val file: File = File(plugin.dataFolder, name)

    protected abstract var configuration: T

    init {
        setupFile()
    }

    private fun setupFile() {
        if (!file.exists()) {
            file.parentFile?.mkdirs()
            try {
                plugin.saveResource(name, false)
            } catch (e: Exception) {
                try { file.createNewFile() } catch (_: IOException) {}
            }
        }
    }

    abstract fun load(): T
    abstract fun save()

    abstract fun write(path: String, value: Any?, saveAfter: Boolean = true)

    abstract fun remove(path: String, saveAfter: Boolean = true)

    abstract fun isEmpty(): Boolean

    fun reload() {
        load()
    }

    fun getConfig(): T = configuration

    fun getContent(): String = runCatching { file.readText() }.getOrDefault("")

    fun exists(): Boolean = file.exists()

    fun delete(): Boolean = file.delete()

    fun backup(suffix: String = ".bak"): Boolean {
        return try {
            file.copyTo(File(file.parentFile, "$name$suffix"), overwrite = true)
            true
        } catch (e: Exception) {
            plugin.logger.warning("Failed to backup $name: ${e.message}")
            false
        }
    }
}