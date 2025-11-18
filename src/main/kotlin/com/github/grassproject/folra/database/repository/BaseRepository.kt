package com.github.grassproject.folra.database.repository

import com.github.grassproject.folra.database.dao.BaseDAO

abstract class BaseRepository<T, ID>(protected val dao: BaseDAO<T, ID>) {
    fun create(entity: T) = dao.insert(entity)
    fun update(entity: T) = dao.update(entity)
    fun delete(id: ID) = dao.delete(id)
    fun getById(id: ID) = dao.findById(id)
    fun getAll() = dao.findAll()
}