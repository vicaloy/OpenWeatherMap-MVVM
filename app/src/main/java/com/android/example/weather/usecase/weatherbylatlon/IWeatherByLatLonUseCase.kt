package com.android.example.weather.usecase.weatherbylatlon

import com.android.example.weather.data.network.RequestCompleteListener
import com.android.example.weather.data.network.WeatherInfoResponse


interface IWeatherByLatLonUseCase {
    fun getWeatherByLatLon(lat: String, lon:String, callback: RequestCompleteListener<WeatherInfoResponse>)
}