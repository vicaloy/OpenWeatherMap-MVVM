

package com.android.example.github.presentation.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.github.R
import com.android.example.github.data.repository.RepoRepository
import javax.inject.Inject

class RegisterViewModel @Inject constructor(repoRepository: RepoRepository) : ViewModel() {

    val register: RegisterObservable = RegisterObservable()

    private val _loginForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _loginForm

    fun onRegisterClick(){
        registerDataChanged()
    }

    private fun registerDataChanged() {
        if (!isNameValid(register.name)) {
            _loginForm.value = RegisterFormState(nameError = R.string.invalid_username)
        } else if (!isEmailValid(register.email)) {
            _loginForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (!isBirthdayValid(register.birthday)) {
            _loginForm.value = RegisterFormState(birthdayError = R.string.invalid_birthday)
        } else {
            _loginForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isEmailValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isNameValid(name: String): Boolean {
        return name.length > 5
    }

    private fun isBirthdayValid(birthday: String): Boolean {
        return birthday.isNotEmpty()
    }
}
