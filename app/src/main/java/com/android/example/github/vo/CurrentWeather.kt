package com.android.example.github.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

@Entity
data class CurrentWeather(
    @PrimaryKey val uid: Int,
    val temp: Double = 0.0,
    val pressure: Double = 0.0,
    val humidity: Int = 0,
    val wind: Double = 0.0,
    val timestamp: String = Timestamp(System.currentTimeMillis()).toString()
)