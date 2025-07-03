package com.example.taller.login.usecase

interface LoginUseCase {

    fun loginUser(email: String, password: String): Boolean

}