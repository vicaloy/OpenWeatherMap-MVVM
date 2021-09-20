package com.android.example.github.data.repository

import com.android.example.github.data.network.RequestCompleteListener
import com.android.example.github.data.prefs.Prefs
import com.android.example.github.vo.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RegisterRepository @Inject constructor(
    private val prefs: Prefs
) {

    fun register(registerUser: RegisterUser, callback: RequestCompleteListener<RegisterUser>) {
        prefs.register(registerUser, callback)
    }

    fun getUser(callback: RequestCompleteListener<RegisterUser>) {
        prefs.getUser(callback)
    }
}
