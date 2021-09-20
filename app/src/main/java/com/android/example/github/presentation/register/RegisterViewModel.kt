package com.android.example.github.presentation.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.github.R
import com.android.example.github.data.network.RequestCompleteListener
import com.android.example.github.data.repository.RegisterRepository
import com.android.example.github.vo.RegisterUser
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) :
    ViewModel() {

    val register: RegisterObservable = RegisterObservable()

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm


    fun onRegisterClick() {
        registerDataChanged()
    }

    fun getUser() {
        repository.getUser(
            object :
                RequestCompleteListener<RegisterUser> {
                override fun onRequestSuccess(data: RegisterUser) {
                    _registerForm.value = RegisterFormState(
                        isDataValid = true,
                        registerUse = data
                    )
                }

                override fun onRequestFailed(errorMessage: String) {
                    _registerForm.value = RegisterFormState(isDataValid = false)
                }
            })

    }



    private fun registerDataChanged() {
        if (!isNameValid(register.name)) {
            _registerForm.value = RegisterFormState(nameError = R.string.invalid_username)
        } else if (!isEmailValid(register.email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (!isBirthdayValid(register.birthday)) {
            _registerForm.value = RegisterFormState(birthdayError = R.string.invalid_birthday)
        } else {
            repository.register(RegisterUser(register.name, register.email, register.birthday),
                object :
                    RequestCompleteListener<RegisterUser> {
                    override fun onRequestSuccess(data: RegisterUser) {
                        _registerForm.value = RegisterFormState(isDataValid = true)
                    }

                    override fun onRequestFailed(errorMessage: String) {
                        _registerForm.value = RegisterFormState(isDataValid = false)
                    }
                })

        }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun isBirthdayValid(birthday: String): Boolean {
        val regex =
            Regex("^((0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01])[- /.](19|20)?[0-9]{2})*$")
        return (birthday.matches(regex) && birthday.isNotBlank())
    }
}
