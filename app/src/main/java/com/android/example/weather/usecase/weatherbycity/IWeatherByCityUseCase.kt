package com.android.example.weather.usecase.weatherbycity

import com.android.example.weather.data.network.RequestCompleteListener
import com.android.example.weather.data.network.WeatherInfoResponse


interface IWeatherByCityUseCase {
    fun getWeatherByCity(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}