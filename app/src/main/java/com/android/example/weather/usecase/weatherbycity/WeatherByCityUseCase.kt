package com.android.example.weather.usecase.weatherbycity

import com.android.example.weather.data.network.RequestCompleteListener
import com.android.example.weather.data.network.WeatherInfoResponse
import com.android.example.weather.data.network.WeatherService


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherByCityUseCase @Inject constructor(
    private val weatherService: WeatherService
) : IWeatherByCityUseCase {

    override fun getWeatherByCity(
        cityId:Int,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {

        val call: Call<WeatherInfoResponse> = weatherService.getWeatherByCity(cityId)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(requireNotNull(response.body()))
                else
                    callback.onRequestFailed(response.message())
            }

            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(requireNotNull(t.localizedMessage))
            }
        })
    }
}