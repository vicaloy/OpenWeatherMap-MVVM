

package com.android.example.github.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.example.github.vo.*

/**
 * Main database description.
 */
@Database(
    entities = [
        User::class,
        Repo::class,
        Contributor::class,
        RepoSearchResult::class,
        CurrentWeather::class],
    version = 4,
    exportSchema = false
)
abstract class GithubDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao

    abstract fun weatherDao(): WeatherDao
}
