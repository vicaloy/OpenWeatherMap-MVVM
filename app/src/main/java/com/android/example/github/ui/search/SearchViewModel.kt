/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.github.ui.search

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.android.example.github.R
import com.android.example.github.repository.RepoRepository
import com.android.example.github.testing.OpenForTesting
import com.android.example.github.util.AbsentLiveData
import com.android.example.github.vo.Repo
import com.android.example.github.vo.Resource
import com.android.example.github.vo.Status
import java.util.Locale
import javax.inject.Inject

@OpenForTesting
class SearchViewModel @Inject constructor(repoRepository: RepoRepository) : ViewModel() {

    val register: RegisterObservable = RegisterObservable()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun onRegisterClick(){
        registerDataChanged()
        register
    }

    fun registerDataChanged() {
        if (!isNameValid(register.name)) {
            _loginForm.value = LoginFormState(nameError = R.string.invalid_username)
        } else if (!isEmailValid(register.email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isBirthdayValid(register.birthday)) {
            _loginForm.value = LoginFormState(birthdayError = R.string.invalid_birthday)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
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
