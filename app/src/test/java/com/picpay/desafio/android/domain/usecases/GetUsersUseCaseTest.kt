package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.model.UserListState
import com.picpay.desafio.android.domain.repositories.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetUsersUseCaseTest {

    private val repository = mockk<UserRepository>()
    private val useCase by lazy { GetUsersUseCase(repository) }

    private val user = listOf(User(img = "img", name = "name", id = 1, username = "username"))
    private val userListStateLocal = UserListState(user, isOffline = true)
    private val userListStateRemote = UserListState(user, isOffline = false)

    @Test
    fun `Should return user list from remote when api call success`(): Unit = runBlocking {
        coEvery { repository.getUsers() } returns flowOf(userListStateRemote)

        val actual = useCase.invoke()
        val expected = userListStateRemote
        assertEquals(expected, actual.last())
    }

    @Test
    fun `Should return user list from local`(): Unit = runBlocking {
        coEvery { repository.getUsers() } returns flowOf(userListStateLocal)

        val actual = useCase.invoke()
        val expected = userListStateLocal
        assertEquals(expected, actual.first())
    }

    @Test
    fun `Should throw an exception when api call fails`(): Unit = runBlocking {
        val errorFlow = flow<UserListState> {
            throw Exception("error")
        }
        coEvery { repository.getUsers() } returns errorFlow

        useCase.invoke().catch { error ->
            assertEquals(errorFlow.last().error, error.message)
        }
    }
}