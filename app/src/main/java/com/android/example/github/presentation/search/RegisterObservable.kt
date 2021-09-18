package com.android.example.github.presentation.search

import com.android.example.github.BR

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable


class RegisterObservable : BaseObservable() {

    @get:Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var email: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    @get:Bindable
    var birthday: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.birthday)
        }

    fun onNameTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        name=s.toString()
    }

    fun onEmailTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        email=s.toString()
    }

    fun onBirthdayTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        birthday=s.toString()
    }
}