package com.picpay.desafio.android.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.source.local.dao.UserDao
import com.picpay.desafio.android.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}