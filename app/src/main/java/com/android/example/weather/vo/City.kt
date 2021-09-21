package com.android.example.weather.vo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("country")
        val country: String = "",
        @SerializedName("coord")
        val latlong: LatLong=LatLong(0.0,0.0)): Serializable