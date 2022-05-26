package com.picpay.desafio.android.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val img: String,
    val username: String
)

fun List<UserEntity>.toDomain(): List<User> {
    return map {
        User(
            id = it.id,
            name = it.name,
            img = it.img,
            username = it.username
        )
    }
}

fun List<User>.toEntity(): List<UserEntity> {
    return map {
        UserEntity(
            id = it.id,
            name = it.name,
            img = it.img,
            username = it.username
        )
    }
}