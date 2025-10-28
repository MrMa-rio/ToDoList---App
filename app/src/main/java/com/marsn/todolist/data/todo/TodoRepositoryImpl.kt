package com.marsn.todolist.data.todo

import com.marsn.todolist.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {

    override suspend fun insert(title: String, id: Long?, description: String?) {
        val entity = id?.let {
            todoDao.getById(id)?.copy(
                title = title,
                description = description
            )
        } ?: TodoEntity(
            title = title,
            description = description,
            isCompleted = false
        )
        todoDao.insert(entity)
    }

    override suspend fun updateIsCompleted(id: Long, isCompleted: Boolean) {


        val existingTodo = todoDao.getById(id)
        if (existingTodo != null) {
            val updatedTodo = existingTodo.copy(isCompleted = isCompleted)
            todoDao.insert(updatedTodo)
        }
    }

    override suspend fun delete(id: Long) {

        val existingTodo = todoDao.getById(id) ?: return
        todoDao.delete(existingTodo)

    }

    override fun getAll(): Flow<List<Todo>> {

        return todoDao.getAll().map { entities ->
            entities.map { entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    isCompleted = entity.isCompleted
                )
            }
        }
    }


    override suspend fun getById(id: Long): Todo? {

        return todoDao.getById(id)?.let {
            Todo(
                id = it.id,
                title = it.title,
                description = it.description,
                isCompleted = it.isCompleted
            )
        }
    }
}