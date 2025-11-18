package com.github.grassproject.folra.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class SQLIteDataSource(
    databasePath: String,
) : HikariDataSource(HikariConfig().apply {
    this.driverClassName = "org.sqlite.JDBC"
    this.jdbcUrl = "jdbc:sqlite:$databasePath"
    this.poolName = "folra"
})