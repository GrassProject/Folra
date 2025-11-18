package com.github.grassproject.folra.database.dao

interface BaseDAO<T, ID> {
    fun insert(entity: T): Boolean
    fun update(entity: T): Boolean
    fun delete(id: ID): Boolean
    fun findById(id: ID): T?
    fun findAll(): List<T>
}