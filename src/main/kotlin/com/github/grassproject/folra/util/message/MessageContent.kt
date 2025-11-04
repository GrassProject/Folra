package com.github.grassproject.folra.util.message

interface MessageContent {
    val lines: List<String>

    fun replace(from: String, to: String): MessageContent
    fun replace(updater: (String) -> String): MessageContent
}