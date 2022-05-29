package com.picpay.desafio.android.presentation.viewmodel

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.model.UserListState
import com.picpay.desafio.android.domain.usecases.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelTest: CoroutineViewModelTest() {

    private val useCase = mockk<GetUsersUseCase>()
    private val viewModel by lazy { UserViewModel(useCase) }

    private val user = listOf(User(img = "img", name = "name", id = 1, username = "username"))
    private val userListStateLocal = UserListState(user, isOffline = true)
    private val userListStateRemote = UserListState(user, isOffline = false)

    @Test
    fun `Should post users list and offline false when repository succeeds from remote`() {
        coEvery { useCase.invoke() } returns flowOf(userListStateRemote)

        viewModel.getUsers()

        viewModel.userResult.observeForever { result ->
            assertEquals(result.users, user)
            assertFalse(result.isOffline)
            assertNull(result.error)
        }
    }

    @Test
    fun `Should post users list and offline true when repository succeeds from local`() {
        coEvery { useCase.invoke() } returns flowOf(userListStateLocal)

        viewModel.getUsers()

        viewModel.userResult.observeForever { result ->
            assertEquals(result.users, user)
            assertTrue(result.isOffline)
            assertNull(result.error)
        }
    }

    @Test
    fun `Should accuse error when get users throws exception`() = runBlocking {
        val errorFlow = flow<UserListState> {
            throw Throwable("error")
        }

        coEvery { useCase.invoke() } returns errorFlow

        viewModel.getUsers()

        viewModel.userResult.observeForever {
            assertEquals("error", it.error)
            assertNull(it.users)
        }
    }
}