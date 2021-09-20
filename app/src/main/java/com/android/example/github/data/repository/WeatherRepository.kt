

package com.android.example.github.data.repository

import com.android.example.github.data.network.RequestCompleteListener
import com.android.example.github.data.network.WeatherInfoResponse
import com.android.example.github.data.db.WeatherDao
import com.android.example.github.data.prefs.Prefs
import com.android.example.github.usecase.WeatherInfoUseCase
import com.android.example.github.vo.CurrentWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepository @Inject constructor(
    private val weatherInfoUseCase: WeatherInfoUseCase,
    private val weatherDao: WeatherDao,
    private val prefs: Prefs
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

    fun logout(callback: RequestCompleteListener<Boolean>){
        prefs.logout(callback)
    }
}
