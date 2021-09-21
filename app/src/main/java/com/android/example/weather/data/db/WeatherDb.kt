

package com.android.example.weather.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.weather.vo.*


@Database(
    entities = [
        CurrentWeather::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDb : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}
