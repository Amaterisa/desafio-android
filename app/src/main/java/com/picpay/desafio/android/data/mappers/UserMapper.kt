package com.picpay.desafio.android.data.mappers

import com.picpay.desafio.android.data.source.local.entity.UserEntity
import com.picpay.desafio.android.data.source.remote.UserResponse
import com.picpay.desafio.android.domain.model.User

class UserMapper {
    fun toUserFromEntity(usersEntity: List<UserEntity>): List<User> {
        return usersEntity.map {
            User(
                id = it.id,
                name = it.name,
                img = it.img,
                username = it.username
            )
        }
    }

    fun toEntityFromUser(users: List<User>): List<UserEntity> {
        return users.map {
            UserEntity(
                id = it.id,
                name = it.name,
                img = it.img,
                username = it.username
            )
        }
    }

    fun toUserFromResponse(usersResponse: List<UserResponse>): List<User> {
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