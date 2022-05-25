package com.picpay.desafio.android.network

import com.picpay.desafio.android.data.source.remote.UserResponse
import com.picpay.desafio.android.model.User
import retrofit2.Response
import retrofit2.http.GET


interface UserApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}