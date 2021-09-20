

package com.android.example.github.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.android.example.github.BuildConfig
import com.android.example.github.R
import com.android.example.github.api.GithubService
import com.android.example.github.data.db.GithubDb
import com.android.example.github.data.db.RepoDao
import com.android.example.github.data.db.UserDao
import com.android.example.github.data.db.WeatherDao
import com.android.example.github.data.network.WeatherService
import com.android.example.github.data.network.QueryParameterAddInterceptor
import com.android.example.github.data.prefs.PreferencesDataSource
import com.android.example.github.usecase.IWeatherInfoUseCase
import com.android.example.github.usecase.WeatherInfoUseCase
import com.android.example.github.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): GithubService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(QueryParameterAddInterceptor())
        val client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room
            .databaseBuilder(app, GithubDb::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(app.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideUserDao(db: GithubDb): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.repoDao()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: GithubDb): WeatherDao {
        return db.weatherDao()
    }

    @Singleton
    @Provides
    fun provideWeatherInfoUseCase(weatherInfoUseCase: WeatherInfoUseCase): IWeatherInfoUseCase{
        return WeatherInfoUseCase(provideWeatherService())
    }


}
