package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.repositories.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getUsers()
}