package com.picpay.desafio.android.repositories

import com.picpay.desafio.android.data.source.local.UserDao
import com.picpay.desafio.android.data.source.local.toDomain
import com.picpay.desafio.android.data.source.local.toEntity
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.network.Result
import com.picpay.desafio.android.network.UserApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao
) {
    fun getUsers() = flow {
        emit(userDao.getAll().toDomain())
        val result = getUsersRemote()
        if (result is Result.Success) {
            deleteUsers()
            insertUsers(result.data)
            emit(userDao.getAll().toDomain())
        }
    }

    private suspend fun deleteUsers() {
        userDao.deleteAll()
    }

    private suspend fun insertUsers(users: List<User>) {
        userDao.insertAll(users.toEntity())
    }

    private suspend fun getUsersRemote(): Result<List<User>> =
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