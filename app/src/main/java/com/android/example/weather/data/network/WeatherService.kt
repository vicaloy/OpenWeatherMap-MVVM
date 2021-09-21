package com.android.example.weather.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?")
    fun getWeatherByCity(@Query("id") cityId: Int): Call<WeatherInfoResponse>

    @GET("data/2.5/weather?")
    fun getWeatherByLatLon(@Query("lat") lat: String, @Query("lon") lon: String): Call<WeatherInfoResponse>

}