package com.example.myapplication.di

import com.example.myapplication.login.model.LoginViewModel
import com.example.myapplication.login.repository.UserRepository
import com.example.myapplication.login.repository.UserRepositoryImpl
import com.example.myapplication.login.usecase.LoginUseCase
import com.example.myapplication.login.usecase.LoginUseCaseImpl
import com.example.myapplication.login.usecase.CredentialsValidatorUseCase
import com.example.myapplication.login.usecase.CredentialsValidatorUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
        factory <UserRepository> { UserRepositoryImpl() }
        factory <LoginUseCase> { LoginUseCaseImpl(get<UserRepository>()) }
        factory <CredentialsValidatorUseCase> { CredentialsValidatorUseCaseImpl() }
        viewModel { LoginViewModel(get<LoginUseCase>(), get<CredentialsValidatorUseCase>()) }
}