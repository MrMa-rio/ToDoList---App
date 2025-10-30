package com.marsn.minitalk.ui.feature.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marsn.minitalk.data.todo.TodoDatabaseProvider
import com.marsn.minitalk.data.todo.TodoRepositoryImpl
import com.marsn.minitalk.domain.Todo
import com.marsn.minitalk.navigation.AddEditRoute
import com.marsn.minitalk.ui.UIEvent
import com.marsn.minitalk.ui.components.TodoItem


@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit
) {

    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provider(context)
    val repository = TodoRepositoryImpl(
        todoDao = database.todoDao
    )
    val viewModel = viewModel<ListViewModel> {
        ListViewModel(
            repository = repository
        )
    }

    val todos by viewModel.todos.collectAsState()


    LaunchedEffect(Unit) {

        viewModel.uiEvent.collect { uiEvent ->

            when (uiEvent) {
                UIEvent.NavigateBack -> {}
                is UIEvent.NavigateTo<*> -> {
                    when (uiEvent.route) {
                        is AddEditRoute -> {
                            navigateToAddEditScreen(uiEvent.route.id)
                        }
                    }
                }
                is UIEvent.ShowSnackbar -> {}
            }

        }

    }


    ListContent(
        todos = todos,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun ListContent(
    todos: List<Todo>,
    onEvent: (ListEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ListEvent.AddEdit(null))
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },

        ) { it

        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onCompletedChange = {
                        onEvent(ListEvent.CompleteChanged(todo.id, it))
                    },
                    onItemClick = {onEvent(ListEvent.AddEdit(todo.id)) },
                    onDeleteClick = {
                        onEvent(ListEvent.Delete(todo.id))
                    }
                )

                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }
}

