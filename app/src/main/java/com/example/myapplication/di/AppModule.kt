package com.example.taller.di

import com.example.taller.login.model.LoginViewModel
import com.example.taller.login.repository.UserRepository
import com.example.taller.login.repository.UserRepositoryImpl
import com.example.taller.login.usecase.LoginUseCase
import com.example.taller.login.usecase.LoginUseCaseImpl
import com.example.taller.login.usecase.CredentialsValidatorUseCase
import com.example.taller.login.usecase.CredentialsValidatorUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
        factory <UserRepository> { UserRepositoryImpl() }
        factory <LoginUseCase> { LoginUseCaseImpl(get<UserRepository>()) }
        factory <CredentialsValidatorUseCase> { CredentialsValidatorUseCaseImpl() }
        viewModel { LoginViewModel(get<LoginUseCase>(), get<CredentialsValidatorUseCase>()) }
}