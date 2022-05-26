package com.picpay.desafio.android.repositories

import com.picpay.desafio.android.data.source.local.UserDao
import com.picpay.desafio.android.data.source.local.toDomain
import com.picpay.desafio.android.data.source.local.toEntity
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.network.Result
import com.picpay.desafio.android.data.api.UserApiService
import com.picpay.desafio.android.data.api.UserResponse
import com.picpay.desafio.android.data.api.toDomain
import com.picpay.desafio.android.model.UserListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao
) {
    fun getUsers() = flow {
        emit(UserListState(userDao.getAll().toDomain()))
        val result = getUsersRemote()
        if (result is Result.Success) {
            insertUsers(result.data.toDomain())
            emit(UserListState(userDao.getAll().toDomain(), isLoading = false))
        } else if (result is Result.Error) {
            emit(UserListState(error = result.exception, isLoading = false))
        }
    }

    private suspend fun insertUsers(users: List<User>) {
        userDao.insertAll(users.toEntity())
    }

    private suspend fun getUsersRemote(): Result<List<UserResponse>> =
        withContext(Dispatchers.IO) {
            try {
                val response = userApiService.getUsers()
                if (response.isSuccessful) {
                    return@withContext Result.Success(response.body()!!)
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}