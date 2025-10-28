package com.marsn.todolist.navigation

import kotlinx.serialization.Serializable

@Serializable
object Route {}

@Serializable
data class AddEditRoute(val id: Long? = null)