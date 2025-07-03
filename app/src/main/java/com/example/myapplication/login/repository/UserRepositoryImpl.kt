package com.example.myapplication.login.repository

class UserRepositoryImpl : UserRepository {

    override fun loginUser(email: String, password: String) : Boolean {
        // api call using retrofit + okttph
        // here you can mock an successful or error result
        return true
    }
}