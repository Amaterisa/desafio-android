package com.picpay.desafio.android.model

data class UserListState(
    val users: List<User>? = null,
    val isLoading: Boolean = true,
    val error: Exception? = null
)