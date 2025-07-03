package com.example.myapplication

import com.example.myapplication.login.usecase.CredentialsValidatorUseCaseImpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CredentialsValidatorUseCaseImplTest {

    private lateinit var validator: CredentialsValidatorUseCaseImpl

    @Before
    fun setup() {
        validator = CredentialsValidatorUseCaseImpl()
    }

    @Test
    fun `valid email should return true`() {
        val result = validator.isValidEmail("user@example.com")
        assertTrue(result)
    }

    @Test
    fun `invalid email missing @ should return false`() {
        val result = validator.isValidEmail("userexample.com")
        assertFalse(result)
    }

    @Test
    fun `empty email should return false`() {
        val result = validator.isValidEmail("")
        assertFalse(result)
    }


    @Test
    fun `valid password with all requirements should return true`() {
        val result = validator.isValidPassword("Abcdef1@")
        assertTrue(result)
    }

    @Test
    fun `password missing special character should return false`() {
        val result = validator.isValidPassword("Abcdef12")
        assertFalse(result)
    }

    @Test
    fun `password shorter than 8 chars should return false`() {
        val result = validator.isValidPassword("A1@b")
        assertFalse(result)
    }

    @Test
    fun `empty password should return false`() {
        val result = validator.isValidPassword("")
        assertFalse(result)
    }

    @Test
    fun `password that is strong should return STRONG`() {
        val result = validator.checkPasswordStrength("StrongP@ssword1")
        assertEquals(CredentialsValidatorUseCaseImpl.PasswordStrength.STRONG, result)
    }

    @Test
    fun `password that is medium should return MEDIUM`() {
        val result = validator.checkPasswordStrength("Abcdef1@")
        assertEquals(CredentialsValidatorUseCaseImpl.PasswordStrength.MEDIUM, result)
    }

    @Test
    fun `weak password should return WEAK`() {
        val result = validator.checkPasswordStrength("abc")
        assertEquals(CredentialsValidatorUseCaseImpl.PasswordStrength.WEAK, result)
    }

    @Test
    fun `empty password should return WEAK`() {
        val result = validator.checkPasswordStrength("")
        assertEquals(CredentialsValidatorUseCaseImpl.PasswordStrength.WEAK, result)
    }

    @Test
    fun `valid email and password should return true`() {
        val result = validator.validateUserFields("user@example.com", "Abcdef1@")
        assertTrue(result)
    }

    @Test
    fun `valid email and invalid password should return false`() {
        val result = validator.validateUserFields("user@example.com", "abc")
        assertFalse(result)
    }

    @Test
    fun `invalid email and valid password should return false`() {
        val result = validator.validateUserFields("userexample.com", "Abcdef1@")
        assertFalse(result)
    }

    @Test
    fun `invalid email and password should return false`() {
        val result = validator.validateUserFields("", "")
        assertFalse(result)
    }
}