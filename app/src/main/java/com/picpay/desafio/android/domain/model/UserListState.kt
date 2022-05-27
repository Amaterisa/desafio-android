package com.picpay.desafio.android.domain.model

data class UserListState(
    val users: List<User>? = null,
    val isOffline: Boolean = true,
    val error: Exception? = null
)