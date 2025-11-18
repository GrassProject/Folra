package com.github.grassproject.folra.database

import com.github.grassproject.folra.config.impl.YamlConfigFile
import com.github.grassproject.folra.api.FolraPlugin
import com.zaxxer.hikari.HikariDataSource
import java.io.File
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class DatabaseManager(
    private val plugin: FolraPlugin,
    // private val configFile: YamlConfigFile = YamlConfigFile(plugin, "database.yml")
) {

    lateinit var dataSource: HikariDataSource
        private set

    val config by lazy { YamlConfigFile(plugin, "database.yml").load() }

    fun init() {
        dataSource = when (config.getString("database.type")?.uppercase()) {
            "MYSQL" -> initMySQL()
            "SQLITE", null -> initSQLite()
            else -> throw IllegalArgumentException("지원하지 않는 데이터베이스 타입: ${config.getString("database.type")}")
        }
    }

    private fun initMySQL(): HikariDataSource {
        val host = config.getString("database.credentials.host") ?: "localhost"
        val port = config.getInt("database.credentials.port", 3306)
        val database = config.getString("database.credentials.database") ?: "database"
        val username = config.getString("database.credentials.username") ?: "root"
        val password = config.getString("database.credentials.password") ?: "password"
        val maxPoolSize = config.getInt("database.pool_options.size", 10)
        return MySQLDataSource(host, port, database, username, password, maxPoolSize)
    }

    private fun initSQLite(): HikariDataSource {
        val path = config.getString("database.sqlite.path")
            ?: File(plugin.dataFolder, "sqlite.db").absolutePath
        val file = File(path)
        if (!file.exists()) file.parentFile.mkdirs()
        return SQLIteDataSource(path)
    }

    fun getConnection(): Connection = dataSource.connection

    private inline fun <T> useConnection(block: (Connection) -> T): T =
        getConnection().use(block)

    private inline fun <T> Connection.usePrepare(sql: String, params: Array<out Any>, block: (PreparedStatement) -> T): T {
        prepareStatement(sql).use { stmt ->
            params.forEachIndexed { index, param -> stmt.setObject(index + 1, param) }
            return block(stmt)
        }
    }

    fun execute(sql: String, vararg params: Any): Boolean =
        useConnection { conn -> conn.usePrepare(sql, params) { it.execute() } }

    fun query(sql: String, vararg params: Any, block: (ResultSet) -> Unit) =
        useConnection { conn -> conn.usePrepare(sql, params) { it.executeQuery().use(block) } }

    fun <T> transaction(block: (Connection) -> T): T =
        useConnection { conn ->
            conn.autoCommit = false
            try {
                val result = block(conn)
                conn.commit()
                result
            } catch (e: Exception) {
                conn.rollback()
                throw e
            }
        }

    fun close() {
        if (this::dataSource.isInitialized) {
            dataSource.close()
        }
    }
}
