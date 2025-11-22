package com.github.grassproject.folra.database

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.config.impl.YamlConfigFile
import java.io.File

class DatabaseManager(
    private val plugin: FolraPlugin
) {

    private var driver: DataDriver? = null

    fun loadDatabase() {
        plugin.dataFolder.mkdirs()
        val file = YamlConfigFile(plugin, "database.yml")
        file.load()

        val config = file.getConfig()
        val type = config.getString("database.type", "SQLITE")!!

        driver = if (type.uppercase() == "SQLITE") {
            val file = File(plugin.dataFolder, "sqlite.db")
            file.createNewFile()
            SQLiteDriver(file.path)
        } else {
            val host = config.getString("database.credentials.host") ?: "localhost"
            val port = config.getInt("database.credentials.port", 3306)
            val database = config.getString("database.credentials.database") ?: "database"
            val username = config.getString("database.credentials.username") ?: "root"
            val password = config.getString("database.credentials.password") ?: "password"
            val maxPoolSize = config.getInt("database.pool_options.size", 10)
            MySqlDriver(host, port, database, username, password, maxPoolSize)
        }
    }

    fun getDataDriver(): DataDriver {
        return driver ?: throw IllegalStateException("Database not loaded. Call loadDatabase() first.")
    }
}