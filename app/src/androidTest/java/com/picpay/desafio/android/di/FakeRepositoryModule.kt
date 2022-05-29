package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.repositories.FakeUserRepository
import com.picpay.desafio.android.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
interface FakeRepositoryModule {
    @Binds
    @Singleton
    fun provideUserRepository(fakeUserRepository: FakeUserRepository): UserRepository
}