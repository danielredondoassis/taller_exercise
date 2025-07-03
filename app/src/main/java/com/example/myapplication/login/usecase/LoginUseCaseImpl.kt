package com.example.myapplication.login.usecase

import com.example.myapplication.login.repository.UserRepository

class LoginUseCaseImpl(private val userRepository: UserRepository) : LoginUseCase {

    override fun loginUser(email: String, password: String) : Boolean {
        return userRepository.loginUser(email, password)
    }
}