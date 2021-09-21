package com.android.example.weather.vo

import com.google.gson.annotations.SerializedName

data class LatLong (
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("lat")
    val lat: Double = 0.0)