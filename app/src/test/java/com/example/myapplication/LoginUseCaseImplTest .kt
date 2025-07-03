package com.example.taller

import com.example.taller.login.repository.UserRepository
import com.example.taller.login.usecase.LoginUseCaseImpl
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginUseCaseImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var loginUseCase: LoginUseCaseImpl

    @Before
    fun setup() {
        userRepository = mockk()
        loginUseCase = LoginUseCaseImpl(userRepository)
    }

    @Test
    fun `loginUser returns true when repository returns true`() {
        val email = "user@example.com"
        val password = "Strong1@"
        every { userRepository.loginUser(email, password) } returns true

        val result = loginUseCase.loginUser(email, password)

        assertTrue(result)
    }

    @Test
    fun `loginUser returns false when repository returns false`() {
        val email = "user@example.com"
        val password = "wrongpass"
        every { userRepository.loginUser(email, password) } returns false

        val result = loginUseCase.loginUser(email, password)

        assertFalse(result)
    }
}