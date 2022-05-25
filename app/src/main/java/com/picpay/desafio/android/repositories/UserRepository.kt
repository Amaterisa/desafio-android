package com.picpay.desafio.android.repositories

import com.picpay.desafio.android.network.UserApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiService: UserApiService) {
    suspend fun getUsers() = userApiService.getUsers()
}