

package com.android.example.github.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.example.github.vo.CurrentWeather

@Dao
abstract class WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg weather: CurrentWeather)

    @Query("SELECT * FROM currentweather")
    abstract fun load(): LiveData<CurrentWeather>
}
