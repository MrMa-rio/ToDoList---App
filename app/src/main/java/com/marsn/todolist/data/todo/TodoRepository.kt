package com.marsn.todolist.data.todo

import com.marsn.todolist.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insert(title: String, id: Long?,  description: String?)

    suspend fun updateIsCompleted(id: Long, isCompleted: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Todo>>

    suspend fun getById(id: Long): Todo?
}