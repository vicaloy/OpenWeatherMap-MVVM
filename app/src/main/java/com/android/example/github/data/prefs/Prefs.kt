package com.android.example.github.data.prefs

import android.content.SharedPreferences
import com.android.example.github.data.network.RequestCompleteListener
import com.android.example.github.vo.RegisterUser
import javax.inject.Inject

class Prefs @Inject constructor(val sharedPref: SharedPreferences) {
    private val TAG = "Prefs"

    fun register(registerUser: RegisterUser, callback: RequestCompleteListener<RegisterUser>) {
        try {
            with(sharedPref.edit()) {
                putString("username", registerUser.name)
                putString("email", registerUser.email)
                putString("birthday", registerUser.birthday)
                apply()
            }

            callback.onRequestSuccess(registerUser)
        } catch (e: Throwable) {
            callback.onRequestFailed(e.message.toString())
        }
    }

    fun logout(callback: RequestCompleteListener<Boolean>) {
        try {
            sharedPref.edit().clear().apply()

            callback.onRequestSuccess(true)

        } catch (e: Throwable) {
            callback.onRequestFailed(e.message.toString())
        }
    }

    fun getUser(callback: RequestCompleteListener<RegisterUser>) {

        try {
            return callback.onRequestSuccess(
                RegisterUser(
                    sharedPref.getString(
                        "username",
                        ""
                    ) ?: "",
                    sharedPref.getString(
                        "email",
                        ""
                    ) ?: "",
                    sharedPref.getString(
                        "birthday",
                        ""
                    ) ?: ""
                )
            )
        } catch (e: Throwable) {
            callback.onRequestFailed(e.message.toString())
        }

    }
}