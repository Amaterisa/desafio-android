package com.picpay.desafio.android.data.repositories

import com.picpay.desafio.android.data.mappers.UserMapper
import com.picpay.desafio.android.data.source.local.FakeUserDao
import com.picpay.desafio.android.data.source.remote.UserApiService
import com.picpay.desafio.android.data.source.remote.UserResponse
import com.picpay.desafio.android.domain.model.UserListState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {
    private val api = mockk<UserApiService>()
    private val dao = FakeUserDao()
    private val mapper = UserMapper()
    private val repository = UserRepositoryImpl(api, dao, mapper)

    private val userResponse = listOf(UserResponse(img = "img", name = "name", id = 1, username = "username"))
    private val userResponseAlternative = listOf(UserResponse(img = "img", name = "name", id = 2, username = "username"))

    private val user = mapper.getUserFromResponse(userResponse)
    private val userEntity = mapper.getEntityFromUser(user)
    private val userListStateLocal = UserListState(user, isOffline = true)
    private val userListStateRemote = UserListState(user, isOffline = false)

    @Before
    fun setupDao(): Unit = runBlocking {
        dao.insertAll(userEntity)
    }

    @Test
    fun `Should return user list when api call success`(): Unit = runBlocking {
        coEvery { api.getUsers() } returns userResponse

        val actual = repository.getUsers().toList()
        val expected = userListStateRemote
        assertEquals(expected, actual.last())
    }

    @Test
    fun `Should return user list from room`(): Unit = runBlocking {
        val actual = repository.getUsers().first()
        val expected = userListStateLocal
        assertEquals(expected, actual)
    }

    @Test
    fun `Should throw an exception when api call fails`(): Unit = runBlocking {
        val throwable = Exception()
        coEvery { api.getUsers() } throws throwable

        repository.getUsers().catch {
            error -> assertEquals(throwable.message, error.message)
        }
    }

    @Test
    fun `Should replace user dao data when api call succeeds`(): Unit = runBlocking {
        coEvery { api.getUsers() } returns userResponseAlternative

        repository.getUsers().toList()
        val expected = mapper.getUserFromResponse(userResponseAlternative)
        val actual = repository.getUsersLocal()
        assertEquals(expected, actual)
    }
}