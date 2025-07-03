package com.example.taller.login.usecase

import com.example.taller.login.repository.UserRepository

class LoginUseCaseImpl(private val userRepository: UserRepository) : LoginUseCase {

    override fun loginUser(email: String, password: String) : Boolean {
        return userRepository.loginUser(email, password)
    }
}