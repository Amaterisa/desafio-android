package com.picpay.desafio.android.data.mappers

import com.picpay.desafio.android.data.source.local.entity.UserEntity
import com.picpay.desafio.android.data.source.remote.UserResponse
import com.picpay.desafio.android.domain.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {
    private val mapper = UserMapper()

    private val userResponse = listOf(UserResponse(img = "img", name = "name", id = 1, username = "username"))
    private val user = listOf(User(img = "img", name = "name", id = 1, username = "username"))
    private val userEntity = listOf(UserEntity(img = "img", name = "name", id = 1, username = "username"))

    @Test
    fun `Should map user response to user`() {
        val actual = mapper.getUserFromResponse(userResponse)
        val expected = user
        assertEquals(expected, actual)
    }

    @Test
    fun `Should map user entity to user`() {
        val actual = mapper.getUserFromEntity(userEntity)
        val expected = user
        assertEquals(expected, actual)
    }

    @Test
    fun `Should map user to user entity`() {
        val actual = mapper.getEntityFromUser(user)
        val expected = userEntity
        assertEquals(expected, actual)
    }
}