package com.example.myapplication.login.repository

interface UserRepository {

    fun loginUser(email: String, password: String) : Boolean
}