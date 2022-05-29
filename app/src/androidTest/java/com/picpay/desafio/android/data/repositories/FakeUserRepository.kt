package com.picpay.desafio.android.data.repositories

import com.picpay.desafio.android.domain.model.UserListState
import com.picpay.desafio.android.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeUserRepository @Inject constructor(): UserRepository {
    var user: UserListState = UserListState()

    override fun getUsers(): Flow<UserListState> {
        return flow { emit(user) }
    }
}