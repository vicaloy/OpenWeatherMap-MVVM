

package com.android.example.github.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.android.example.github.BuildConfig
import com.android.example.github.R
import com.android.example.github.data.db.WeatherDb
import com.android.example.github.data.db.WeatherDao
import com.android.example.github.data.db.WeatherTypeConverters
import com.android.example.github.data.network.WeatherService
import com.android.example.github.data.network.QueryParameterAddInterceptor
import com.android.example.github.usecase.IWeatherInfoUseCase
import com.android.example.github.usecase.WeatherInfoUseCase
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
    fun provideWeatherService(): WeatherService {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(QueryParameterAddInterceptor())
        val client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): WeatherDb {
        return Room
            .databaseBuilder(app, WeatherDb::class.java, "weather.db")
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
    fun provideWeatherDao(db: WeatherDb): WeatherDao {
        return db.weatherDao()
    }

    @Singleton
    @Provides
    fun provideWeatherInfoUseCase(weatherInfoUseCase: WeatherInfoUseCase): IWeatherInfoUseCase{
        return WeatherInfoUseCase(provideWeatherService())
    }


}
