package com.android.example.weather.presentation.register

import com.android.example.weather.vo.RegisterUser

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