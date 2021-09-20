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

import androidx.lifecycle.LifecycleCoroutineScope
import com.android.example.github.api.*
import com.android.example.github.data.db.WeatherDao
import com.android.example.github.usecase.WeatherInfoUseCase
import com.android.example.github.vo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
class WeatherRepository @Inject constructor(
    private val weatherInfoUseCase: WeatherInfoUseCase,
    private val weatherDao: WeatherDao
) {

    fun loadWeather(cityId: Int, callback: RequestCompleteListener<CurrentWeather>) {
        weatherInfoUseCase.loadWeather(cityId, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                CoroutineScope(Dispatchers.IO).launch {
                    val currentWeather = CurrentWeather(
                        Random().nextInt(),
                        data.main.temp,
                        data.main.pressure,
                        data.main.humidity,
                        data.wind.speed,
                        Timestamp(System.currentTimeMillis()).toString()
                    )


                    weatherDao.insert(currentWeather)

                    callback.onRequestSuccess(currentWeather)
                }

            }

            override fun onRequestFailed(errorMessage: String) {
                callback.onRequestFailed(errorMessage)
            }
        })
    }
}
