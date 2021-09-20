

package com.android.example.github.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.github.vo.*

/**
 * Main database description.
 */
@Database(
    entities = [
        CurrentWeather::class],
    version = 4,
    exportSchema = false
)
abstract class WeatherDb : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}
