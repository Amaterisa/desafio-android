package com.picpay.desafio.android.data.source.local.dao

import androidx.room.*
import com.picpay.desafio.android.data.source.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * from users")
    suspend fun getAll(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("DELETE from users")
    suspend fun deleteAll()
}