package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.mappers.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }
}