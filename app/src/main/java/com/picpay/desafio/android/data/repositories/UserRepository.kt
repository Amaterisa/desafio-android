package com.picpay.desafio.android.data.repositories

import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.mappers.UserMapper
import com.picpay.desafio.android.data.remote.UserApiService
import com.picpay.desafio.android.data.remote.UserResponse
import com.picpay.desafio.android.domain.common.Result
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.model.UserListState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao,
    private val userMapper: UserMapper
) {
    fun getUsers() = flow {
        emit(UserListState(userMapper.toUserFromEntity(userDao.getAll())))
        val result = getUsersRemote()
        if (result is Result.Success) {
            insertUsers(userMapper.toUserFromResponse(result.data))
            emit(UserListState(userMapper.toUserFromEntity(userDao.getAll()), isLoading = false))
        } else if (result is Result.Error) {
            emit(UserListState(error = result.exception, isLoading = false))
        }
    }

    private suspend fun insertUsers(users: List<User>) {
        userDao.insertAll(userMapper.toEntityFromUser(users))
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