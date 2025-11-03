//package com.github.grassproject.folra.config
//
//import java.io.File
//
//interface IConfigFile<T> {
//    val file: File
//    fun load(): T
//    fun save()
//    fun reload()
//    fun exists(): Boolean = file.exists()
//    fun write(path: String, value: Any)
//    fun remove(path: String)
//    fun isEmpty(): Boolean
//    fun delete(): Boolean = file.delete()
//}