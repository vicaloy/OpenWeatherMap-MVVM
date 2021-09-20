

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
