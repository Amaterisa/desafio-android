package com.picpay.desafio.android.data.source.local

import com.picpay.desafio.android.data.source.local.dao.UserDao
import com.picpay.desafio.android.data.source.local.entity.UserEntity

class FakeUserDao: UserDao {
    private val userList = mutableListOf<UserEntity>()

    override suspend fun getAll(): List<UserEntity> {
        return userList
    }

    override suspend fun insertAll(users: List<UserEntity>) {
        userList.addAll(users)
    }

    override suspend fun deleteAll() {
        userList.clear()
    }
}