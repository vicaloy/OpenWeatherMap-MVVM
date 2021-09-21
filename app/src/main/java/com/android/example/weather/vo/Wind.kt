package com.android.example.weather.vo


import com.google.gson.annotations.SerializedName

data class Wind(
        @SerializedName("speed")
        val speed: Double = 0.0,
        @SerializedName("deg")
        val deg: Double = 0.0
)