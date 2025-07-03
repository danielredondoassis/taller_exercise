package com.example.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.login.model.LoginViewModel
import com.example.myapplication.login.usecase.CredentialsValidatorUseCase
import com.example.myapplication.login.usecase.CredentialsValidatorUseCaseImpl
import com.example.myapplication.login.usecase.LoginUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val loginUseCase: LoginUseCase = mockk()
    private val validator: CredentialsValidatorUseCase = mockk()
    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(loginUseCase, validator)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `validateUserCredentialsAndLogin logs in on valid input`() = runTest {
        val email = "user@example.com"
        val password = "Strong1@"

        every { validator.validateUserFields(email, password) } returns true
        coEvery { loginUseCase.loginUser(email, password) } returns true

        viewModel.validateUserCredentialsAndLogin(email, password)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.isLoggedIn)
        assertFalse(state.isLoading)
        assertFalse(state.loginError)
        assertFalse(state.emailError)
        assertFalse(state.passwordError)
    }

    @Test
    fun `validateUserCredentialsAndLogin shows errors on invalid input`() = runTest {
        val email = "bademail"
        val password = "weak"

        every { validator.validateUserFields(email, password) } returns false
        every { validator.isValidEmail(email) } returns false
        every { validator.isValidPassword(password) } returns false

        viewModel.validateUserCredentialsAndLogin(email, password)

        val state = viewModel.uiState.value
        assertFalse(state.isLoggedIn)
        assertTrue(state.emailError)
        assertTrue(state.passwordError)
    }

    @Test
    fun `loginUser sets error on failed login`() = runTest {
        val email = "user@example.com"
        val password = "Strong1@"

        every { validator.validateUserFields(email, password) } returns true
        coEvery { loginUseCase.loginUser(email, password) } returns false

        viewModel.validateUserCredentialsAndLogin(email, password)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoggedIn)
        assertFalse(state.isLoading)
        assertTrue(state.loginError)
    }

    @Test
    fun `checkPasswordStrength updates password strength`() = runTest {
        val password = "Strong1@"

        every { validator.checkPasswordStrength(password) } returns CredentialsValidatorUseCaseImpl.PasswordStrength.STRONG

        viewModel.checkPasswordStrength(password)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(CredentialsValidatorUseCaseImpl.PasswordStrength.STRONG, state.passwordStrength)
        assertFalse(state.passwordError)
    }

    @Test
    fun `validateUserPassword sets passwordError true for invalid password`() {
        every { validator.isValidPassword("123") } returns false

        val method = LoginViewModel::class.java.getDeclaredMethod("validateUserPassword", String::class.java)
        method.isAccessible = true
        method.invoke(viewModel, "123")

        val state = viewModel.uiState.value
        assertTrue(state.passwordError)
    }

    @Test
    fun `validateUserEmail sets emailError true for invalid email`() {
        every { validator.isValidEmail("bademail") } returns false

        val method = LoginViewModel::class.java.getDeclaredMethod("validateUserEmail", String::class.java)
        method.isAccessible = true
        method.invoke(viewModel, "bademail")

        val state = viewModel.uiState.value
        assertTrue(state.emailError)
    }
}