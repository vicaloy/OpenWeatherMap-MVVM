package com.android.example.github.usecase

import com.android.example.github.data.network.RequestCompleteListener
import com.android.example.github.data.network.WeatherInfoResponse


interface IWeatherInfoUseCase {
    fun loadWeather(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}