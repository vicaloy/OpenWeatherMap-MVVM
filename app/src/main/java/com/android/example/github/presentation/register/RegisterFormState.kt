package com.android.example.github.presentation.register

import com.android.example.github.vo.RegisterUser

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val nameError: Int? = null,
    val emailError: Int? = null,
    val birthdayError: Int? = null,
    val isDataValid: Boolean = false,
    val registerUse: RegisterUser?=null
)