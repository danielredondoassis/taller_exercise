package com.example.taller.login.repository

interface UserRepository {

    fun loginUser(email: String, password: String) : Boolean
}