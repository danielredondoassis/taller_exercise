package com.example.myapplication.login.state

import com.example.myapplication.login.usecase.CredentialsValidatorUseCaseImpl

data class LoginUiState(
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val passwordStrength: CredentialsValidatorUseCaseImpl.PasswordStrength? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val loginError: Boolean = false

)