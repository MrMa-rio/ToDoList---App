package com.marsn.todolist.ui.feature.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.marsn.todolist.data.todo.TodoDatabaseProvider
import com.marsn.todolist.data.todo.TodoRepositoryImpl
import com.marsn.todolist.ui.UIEvent

@Composable
fun AddEditToDoScreen(
    id: Long?,
    navigateBack: () -> Unit
) {

    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provider(context)
    val repository = TodoRepositoryImpl(
        todoDao = database.todoDao
    )
    val viewModel = viewModel<AddEditViewModel> {
        AddEditViewModel(
            id = id,
            todoRepository = repository
        )
    }

    val title = viewModel.title
    val description = viewModel.description
    val snackbarHostState = remember() {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is UIEvent.NavigateBack -> {
                    navigateBack()
                }
                is UIEvent.NavigateTo<*> -> {}
            }
        }

    }

    AddEditContent(
        title = title,
        description = description,
        snackbarHostState,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun AddEditContent(
    title: String,
    description: String?,
    hostState: SnackbarHostState,
    onEvent: (AddEditEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(AddEditEvent.Save) }) {
                Icon(Icons.Filled.Check, "Save")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = hostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = {
                    onEvent(AddEditEvent.TitleChanged(it))
                },
                placeholder = { Text("Title") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description ?: "",
                onValueChange = {
                    onEvent(AddEditEvent.DescriptionChanged(it))
                },
                placeholder = { Text("Description (optional)") }
            )

        }
    }
}


@Preview
@Composable
fun AddEditToDoScreenPreview() {
    AddEditToDoScreen(null, navigateBack = {})
}