package com.marsn.minitalk.ui

sealed interface UIEvent {


    data class ShowSnackbar(val message: String) : UIEvent

    data object NavigateBack : UIEvent

    data class NavigateTo<T : Any>(val route: T) : UIEvent

}
