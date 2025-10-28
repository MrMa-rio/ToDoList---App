package com.marsn.minitalk.domain

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean
)


val todos: List<Todo> = listOf(
    Todo(
        id = 1,
        title = "Buy groceries",
        description = "Milk, eggs, bread",
        isCompleted = false
    ),
    Todo(
        id = 2,
        title = "Clean the house",
        description = "Vacuum, dust",
        isCompleted = true
    ),
    Todo(
        id = 3,
        title = "Exercise",
        description = "30 minutes of cardio",
        isCompleted = false
    ),
    Todo(
        id = 4,
        title = "Read a book",
        description = "The Great Gatsby",
        isCompleted = true
    ),
    Todo(
        id = 5,
        title = "Go for a walk",
        description = "30 minutes in the park",
        isCompleted = false
    ),
    Todo(
        id = 6,
        title = "Prepare dinner",
        description = "Pasta, vegetables",
        isCompleted = false
    ),
    Todo(
        id = 1,
        title = "Buy groceries",
        description = "Milk, eggs, bread",
        isCompleted = false
    ),
    Todo(
        id = 2,
        title = "Clean the house",
        description = "Vacuum, dust",
        isCompleted = true
    ),
    Todo(
        id = 3,
        title = "Exercise",
        description = null,
        isCompleted = false
    ),
    Todo(
        id = 4,
        title = "Read a book",
        description = "The Great Gatsby",
        isCompleted = true
    ),
    Todo(
        id = 5,
        title = "Go for a walk",
        description = "30 minutes in the park",
        isCompleted = false
    ),
    Todo(
        id = 6,
        title = "Prepare dinner",
        description = "Pasta, vegetables",
        isCompleted = false
    )
)
