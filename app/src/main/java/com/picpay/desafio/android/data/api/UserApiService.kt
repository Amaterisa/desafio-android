package com.picpay.desafio.android.data.api

import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse>>
}