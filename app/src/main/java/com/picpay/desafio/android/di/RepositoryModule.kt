package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.repositories.UserRepositoryImpl
import com.picpay.desafio.android.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository
}