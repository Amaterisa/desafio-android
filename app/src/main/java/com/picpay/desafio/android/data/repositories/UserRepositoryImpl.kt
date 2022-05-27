package com.picpay.desafio.android.data.repositories

import com.picpay.desafio.android.data.source.local.dao.UserDao
import com.picpay.desafio.android.data.mappers.UserMapper
import com.picpay.desafio.android.data.source.remote.UserApiService
import com.picpay.desafio.android.domain.common.Result
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.model.UserListState
import com.picpay.desafio.android.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao,
    private val userMapper: UserMapper
): UserRepository {
    override fun getUsers() = flow {
        emit(UserListState(userMapper.toUserFromEntity(userDao.getAll())))
        val result = getUsersRemote()
        if (result is Result.Success) {
            insertUsers(result.data)
            emit(UserListState(userMapper.toUserFromEntity(userDao.getAll()), isOffline = false))
        } else if (result is Result.Error) {
            emit(UserListState(error = result.exception, isOffline = false))
        }
    }

    override suspend fun insertUsers(users: List<User>) {
        userDao.insertAll(userMapper.toEntityFromUser(users))
    }

    override suspend fun getUsersRemote(): Result<List<User>> =
        withContext(Dispatchers.IO) {
            try {
                val response = userApiService.getUsers()
                if (response.isSuccessful) {
                    return@withContext Result.Success(userMapper.toUserFromResponse(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}