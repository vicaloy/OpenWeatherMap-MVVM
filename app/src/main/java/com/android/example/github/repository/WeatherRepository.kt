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

package com.android.example.github.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.android.example.github.AppExecutors
import com.android.example.github.api.*
import com.android.example.github.db.GithubDb
import com.android.example.github.db.RepoDao
import com.android.example.github.db.WeatherDao
import com.android.example.github.network.WeatherService
import com.android.example.github.testing.OpenForTesting
import com.android.example.github.util.AbsentLiveData
import com.android.example.github.util.RateLimiter
import com.android.example.github.vo.*
import java.util.*
import java.util.concurrent.TimeUnit
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
class WeatherRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: GithubDb,
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService
) {

    fun loadRepo(cityId: Int): LiveData<Resource<CurrentWeather>> {
        return object : NetworkBoundResource<CurrentWeather, WeatherInfoResponse>(appExecutors) {
            override fun saveCallResult(item: WeatherInfoResponse) {

                weatherDao.insert(
                    CurrentWeather(
                        Random().nextInt(), item.main.temp,
                        item.main.pressure, item.main.humidity, item.wind.speed
                    )
                )
            }

            override fun shouldFetch(data: CurrentWeather?) = data == null

            override fun loadFromDb() = weatherDao.load()

            override fun createCall() = weatherService.getWeatherInfo(cityId)
        }.asLiveData()
    }
}
