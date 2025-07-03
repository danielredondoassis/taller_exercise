package com.example.taller.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.taller.MainActivity
import com.example.taller.R
import com.example.taller.databinding.LoginActivityBinding
import com.example.taller.login.model.LoginViewModel
import com.example.taller.login.usecase.CredentialsValidatorUseCaseImpl
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding
    private val viewModel: LoginViewModel by inject<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)

        setupListeners()
        observeUiState()
        setContentView(binding.root)
    }

    private fun setupListeners() {
        with(binding) {
            tetPassword.addTextChangedListener {
                viewModel.checkPasswordStrength(it.toString())
            }

            btnLogin.setOnClickListener {
                val username = tetEmail.text.toString()
                val password = tetPassword.text.toString()
                viewModel.validateUserCredentialsAndLogin(username, password)
            }
        }
    }

    private fun observeUiState() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                with(binding) {
                    loadingLayout.root.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                    tilEmail.error =
                        if (state.emailError) getString(R.string.login_invalid_email) else null

                    tilPassword.error =
                        if (state.passwordError) {
                            getString(R.string.login_invalid_password)
                        } else {
                            when (state.passwordStrength) {
                                CredentialsValidatorUseCaseImpl.PasswordStrength.WEAK -> getString(R.string.login_password_strength_weak)
                                CredentialsValidatorUseCaseImpl.PasswordStrength.MEDIUM -> getString(
                                    R.string.login_password_strength_medium
                                )

                                CredentialsValidatorUseCaseImpl.PasswordStrength.STRONG -> null
                                null -> null
                            }
                        }

                    tvLoginError.visibility = if (state.loginError) View.VISIBLE else View.GONE

                    if (state.isLoggedIn) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}