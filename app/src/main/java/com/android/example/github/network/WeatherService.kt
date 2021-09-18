package com.android.example.github.network

import androidx.lifecycle.LiveData
import com.android.example.github.api.ApiResponse
import com.android.example.github.api.WeatherInfoResponse
import com.android.example.github.vo.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getWeatherInfo(@Query("id") cityId: Int): LiveData<ApiResponse<WeatherInfoResponse>>
}