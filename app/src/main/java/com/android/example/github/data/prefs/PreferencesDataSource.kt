package com.android.example.github.data.prefs

import android.content.SharedPreferences
import com.android.example.github.api.RequestCompleteListener
import com.android.example.github.data.repository.WeatherRepository
import com.android.example.github.vo.LoggedInUser
import javax.inject.Inject

class PreferencesDataSource@Inject constructor(val sharedPref: SharedPreferences) {
    private val TAG = "PreferencesDataSource"

    fun register(loggedInUser: LoggedInUser, callback: RequestCompleteListener<LoggedInUser>){
          try{  with(sharedPref.edit()) {
                putString("username", loggedInUser.name)
                putString("email", loggedInUser.email)
                putString("birthday", loggedInUser.birthday)
                apply()
            }

            callback.onRequestSuccess(loggedInUser)
        } catch (e: Throwable) {
            callback.onRequestFailed(e.message.toString())
        }
    }

    fun logout(callback: RequestCompleteListener<LoggedInUser>){
        try {
            sharedPref.edit().clear().apply()

            callback.onRequestSuccess(LoggedInUser("", "", ""))

        } catch (e: Throwable) {
            callback.onRequestFailed(e.message.toString())
        }
    }

    fun getUser(callback: RequestCompleteListener<LoggedInUser>) {

        try {
            return callback.onRequestSuccess(
                LoggedInUser(
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