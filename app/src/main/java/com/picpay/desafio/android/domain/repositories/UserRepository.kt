package com.picpay.desafio.android.domain.repositories

import com.picpay.desafio.android.domain.model.UserListState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<UserListState>
}