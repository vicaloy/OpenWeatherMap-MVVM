package com.android.example.weather.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.example.weather.util.format

import java.util.*

@Entity
data class CurrentWeather(
    @PrimaryKey val cityId: Int = 0,
    val temp: String = "",
    val pressure: String = "",
    val humidity: String = "",
    val wind: String = "",
    val res: Int = 0,
    val cityCountry: String = "",
    val date: String = Date().format()
)