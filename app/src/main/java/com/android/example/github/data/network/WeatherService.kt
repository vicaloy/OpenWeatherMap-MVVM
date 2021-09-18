package com.android.example.github.data.network

import androidx.lifecycle.LiveData
import com.android.example.github.api.ApiResponse
import com.android.example.github.api.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getWeatherInfo(@Query("id") cityId: Int): Call<WeatherInfoResponse>
}