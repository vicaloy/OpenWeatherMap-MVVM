package com.android.example.github.ui.search

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val nameError: Int? = null,
    val emailError: Int? = null,
    val birthdayError: Int? = null,
    val isDataValid: Boolean = false
)