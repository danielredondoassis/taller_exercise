package com.example.taller.login.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller.login.state.LoginUiState
import com.example.taller.login.usecase.LoginUseCase
import com.example.taller.login.usecase.CredentialsValidatorUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCaseImpl: LoginUseCase,
    private val credentialsValidatorUseCase: CredentialsValidatorUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private var passwordJob: Job? = null
    private var loginJob: Job? = null

    private fun validateUserEmail(email: String) {
        val isValidEmail = credentialsValidatorUseCase.isValidEmail(email)
        _uiState.update {
            it.copy(emailError = !isValidEmail)
        }
    }

    private fun validateUserPassword(password: String) {
        val isValidPassword = credentialsValidatorUseCase.isValidPassword(password)
        _uiState.update {
            it.copy(passwordError = !isValidPassword)
        }
    }

    fun checkPasswordStrength(password: String) {
        passwordJob?.cancel()
        passwordJob = viewModelScope.launch {
            val passwordStrength = credentialsValidatorUseCase.checkPasswordStrength(password)
            _uiState.update {
                it.copy(
                    passwordError = false,
                    passwordStrength = passwordStrength
                )
            }
        }
    }

    fun validateUserCredentialsAndLogin(email: String, password: String) {
        if (credentialsValidatorUseCase.validateUserFields(email, password)) {
            loginUser(email, password)
        } else {
            validateUserPassword(password)
            validateUserEmail(email)
        }
    }

    private fun loginUser(email: String, password: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                passwordError = false,
                emailError = false
            )
        }
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            delay(400)
            val result = loginUseCaseImpl.loginUser(email, password)
            if (result) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        loginError = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        loginError = true
                    )
                }
            }
        }
    }
}