package com.example.myapplication.login.usecase

import com.example.myapplication.login.usecase.CredentialsValidatorUseCaseImpl.PasswordStrength

interface CredentialsValidatorUseCase {

    fun validateUserFields(email: String, password: String): Boolean

    fun isValidEmail(email: String): Boolean

    fun isValidPassword(password: String): Boolean

    fun checkPasswordStrength(password: String): PasswordStrength


}