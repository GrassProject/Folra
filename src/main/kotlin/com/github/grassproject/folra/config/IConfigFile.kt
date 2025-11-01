package com.github.grassproject.folra.config

import java.io.File

interface IConfigFile<T> {
    fun load(): T
    fun save()
    fun reload()
    fun exists(): Boolean
    fun getFile(): File
    fun write(path: String, value: Any)
    fun remove(path: String)
    fun isEmpty(): Boolean
}