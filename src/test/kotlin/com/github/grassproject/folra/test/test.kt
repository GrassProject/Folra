package com.github.grassproject.folra.test

import com.google.gson.JsonParser
import java.net.URI

fun main() {
    val url = "https://api.github.com/repos/GrassProject/Folra/releases/latest"
    val jsonString = URI(url).toURL().readText()
    val jsonObject = JsonParser.parseString(jsonString).asJsonObject

    val rawTag = jsonObject["tag_name"].asString

    val cleanTag = Regex("""\d+(\.\d+)*""").find(rawTag)?.value ?: rawTag

    println(cleanTag)
}
