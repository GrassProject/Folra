package com.github.grassproject.folra.config

inline fun <reified V> IConfigFile<*>.getOrDefault(path: String, default: V): V =
    (when (this) {
        is JsonConfigFile -> this.getValue<V>(path)
        is YamlConfigFile -> this.getValue<V>(path)
        else -> null
    }) ?: default
