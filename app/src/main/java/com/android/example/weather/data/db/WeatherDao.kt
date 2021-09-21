

package com.android.example.weather.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.example.weather.vo.CurrentWeather

@Dao
abstract class WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg weather: CurrentWeather)

    @Query("SELECT * FROM currentweather WHERE cityId=:id")
    abstract fun load(id:Int): CurrentWeather?
}
