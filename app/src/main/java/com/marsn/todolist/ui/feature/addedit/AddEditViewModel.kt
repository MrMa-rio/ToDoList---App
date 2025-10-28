package com.marsn.todolist.ui.feature.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marsn.todolist.data.todo.TodoRepository
import com.marsn.todolist.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val id: Long? = null,
    private val todoRepository: TodoRepository
) : ViewModel() {


    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UIEvent>()

    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        id?.let {
            viewModelScope.launch {
                val todo = todoRepository.getById(id)
                title = todo?.title ?: ""
                description = todo?.description
            }
        }

    }


    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.TitleChanged -> {
                title = event.title
            }

            is AddEditEvent.DescriptionChanged -> {
                description = event.description
            }

            is AddEditEvent.Save -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {
        viewModelScope.launch {
            if (title.isBlank()) {
                _uiEvent.send(
                    UIEvent.ShowSnackbar(
                        message = "The title can't be empty"
                    )
                )
                return@launch
            }
            todoRepository.insert(title, id, description)

            _uiEvent.send(UIEvent.NavigateBack)
        }
    }
}