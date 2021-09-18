package com.android.example.github.usecase

import com.android.example.github.api.RequestCompleteListener
import com.android.example.github.api.WeatherInfoResponse
import com.android.example.github.vo.City


interface IWeatherInfoUseCase {
    fun loadWeather(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}