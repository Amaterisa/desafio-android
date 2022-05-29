package com.picpay.desafio.android.data.mappers

import com.picpay.desafio.android.data.source.local.entity.UserEntity
import com.picpay.desafio.android.data.source.remote.UserResponse
import com.picpay.desafio.android.domain.model.User

class UserMapper {
    fun getUserFromEntity(usersEntity: List<UserEntity>): List<User> {
        return usersEntity.map {
            User(
                id = it.id,
                name = it.name,
                img = it.img,
                username = it.username
            )
        }
    }

    fun getEntityFromUser(users: List<User>): List<UserEntity> {
        return users.map {
            UserEntity(
                id = it.id,
                name = it.name,
                img = it.img,
                username = it.username
            )
        }
    }

    fun getUserFromResponse(usersResponse: List<UserResponse>): List<User> {
        return usersResponse.map {
            User(
                id = it.id,
                name = it.name,
                img = it.img,
                username = it.username
            )
        }
    }
}