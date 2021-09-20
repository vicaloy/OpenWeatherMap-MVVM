package com.android.example.github.usecase

import com.android.example.github.data.network.RequestCompleteListener
import com.android.example.github.data.network.WeatherInfoResponse
import com.android.example.github.data.network.WeatherService


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherInfoUseCase @Inject constructor(
    private val weatherService: WeatherService
) : IWeatherInfoUseCase {

    override fun loadWeather(
        cityId: Int,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {

        val call: Call<WeatherInfoResponse> = weatherService.getWeatherInfo(cityId)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(requireNotNull(response.body())) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(requireNotNull(t.localizedMessage)) //let presenter know about failure
            }
        })
    }
}