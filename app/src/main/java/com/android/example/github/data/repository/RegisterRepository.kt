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

package com.android.example.github.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.android.example.github.AppExecutors
import com.android.example.github.api.*
import com.android.example.github.data.db.GithubDb
import com.android.example.github.data.db.WeatherDao
import com.android.example.github.data.network.WeatherService
import com.android.example.github.data.prefs.PreferencesDataSource
import com.android.example.github.testing.OpenForTesting
import com.android.example.github.usecase.WeatherInfoUseCase
import com.android.example.github.vo.*
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Repo instances.
 *
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */
@Singleton
@OpenForTesting
class RegisterRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) {

    fun register(loggedInUser: LoggedInUser, callback: RequestCompleteListener<LoggedInUser>){
        preferencesDataSource.register(loggedInUser, callback)
    }

}
