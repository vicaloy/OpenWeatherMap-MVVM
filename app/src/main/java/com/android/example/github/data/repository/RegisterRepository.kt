
package com.android.example.github.data.repository

import com.android.example.github.api.RequestCompleteListener
import com.android.example.github.data.prefs.PreferencesDataSource
import com.android.example.github.vo.LoggedInUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) {

    fun register(loggedInUser: LoggedInUser, callback: RequestCompleteListener<LoggedInUser>){
        preferencesDataSource.register(loggedInUser, callback)
    }

}
