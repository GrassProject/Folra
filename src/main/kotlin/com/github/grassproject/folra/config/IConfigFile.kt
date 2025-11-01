package com.github.grassproject.folra.config

import java.io.File

interface IConfigFile<T> {
    fun load(): T
    fun save()
    fun exists(): Boolean
    fun getFile(): File
}