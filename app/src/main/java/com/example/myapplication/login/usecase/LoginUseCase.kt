package com.example.myapplication.login.usecase

interface LoginUseCase {

    fun loginUser(email: String, password: String): Boolean

}