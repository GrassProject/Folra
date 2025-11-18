package com.github.grassproject.folra.util

import java.io.File
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object McLogsUtil {

    private val logFile = File("logs/latest.log")
    private val client = HttpClient.newHttpClient()

    fun readLogs(): List<String> =
        runCatching { if (logFile.exists()) logFile.readLines() else emptyList() }
            .getOrElse { emptyList() }

    fun getLastLogs(limit: Int = 10): List<String> =
        readLogs().takeLast(limit)

    fun findLogs(keyword: String): List<String> =
        readLogs().filter { it.contains(keyword, ignoreCase = true) }

    private fun readTextSafe(): String =
        runCatching { if (logFile.exists()) logFile.readText() else "" }
            .getOrDefault("")

    fun uploadLogs(): String? {
        val logText = readTextSafe().takeIf { it.isNotBlank() } ?: return null

        val postBody = "content=${URLEncoder.encode(logText, "UTF-8")}"

        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.mclo.gs/1/log"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(postBody))
            .build()

        val response = runCatching {
            client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        }.getOrNull() ?: return null

        val idRegex = """"id"\s*:\s*"([^"]+)"""".toRegex()
        val match = idRegex.find(response) ?: return null

        return "https://mclo.gs/${match.groupValues[1]}"
    }
}
