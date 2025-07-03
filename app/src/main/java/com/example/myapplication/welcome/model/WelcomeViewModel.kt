package com.example.myapplication.welcome.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Login Successful, Welcome!"
    }
    val text: LiveData<String> = _text
}