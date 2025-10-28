package com.marsn.minitalk.ui.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marsn.minitalk.data.todo.TodoRepository
import com.marsn.minitalk.navigation.AddEditRoute
import com.marsn.minitalk.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    val todos = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.Delete -> {
                eventDelete(event.id)
            }

            is ListEvent.CompleteChanged -> {
                eventCompleteChanged(event.id, event.isCompleted)
            }

            is ListEvent.AddEdit -> {
                viewModelScope.launch {
                    _uiEvent.send(UIEvent.NavigateTo(AddEditRoute(event.id)))
                }
            }

        }
    }

    private fun eventDelete(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }


    private fun eventCompleteChanged(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateIsCompleted(id, isCompleted = isCompleted)
        }

    }


}