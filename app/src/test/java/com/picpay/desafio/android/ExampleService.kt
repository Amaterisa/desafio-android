package com.picpay.desafio.android

import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.data.api.UserApiService

class ExampleService(
    private val service: UserApiService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}