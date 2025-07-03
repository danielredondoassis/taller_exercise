package com.example.myapplication.login.usecase

class CredentialsValidatorUseCaseImpl : CredentialsValidatorUseCase {

    enum class PasswordStrength {
        WEAK, MEDIUM, STRONG
    }

    override fun validateUserFields(email: String, password: String) : Boolean {
        return isValidEmail(email) && isValidPassword(password)
    }

    override fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && email.matches(Regex(REGEX_EMAIL_VALIDATION))
    }

    override fun isValidPassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= PASSWORD_MIN_SIZE && password.contains(Regex(
            REGEX_PASSWORD_VALIDATION
        ))
    }

    override fun checkPasswordStrength(password: String): PasswordStrength {
        return when {
            password.isNotBlank() && password.length >= PASSWORD_MAX_SIZE && password.contains(Regex(
                REGEX_PASSWORD_VALIDATION
            )) -> PasswordStrength.STRONG
            password.isNotBlank() && password.length >= PASSWORD_MIN_SIZE && password.contains(Regex(
                REGEX_PASSWORD_VALIDATION
            )) -> PasswordStrength.MEDIUM
            else -> PasswordStrength.WEAK
        }
    }

    companion object {
        private const val PASSWORD_MIN_SIZE = 8
        private const val PASSWORD_MAX_SIZE = 12
        private const val REGEX_PASSWORD_VALIDATION = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#\$%^&+=])"
        private const val REGEX_EMAIL_VALIDATION = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"

    }
}