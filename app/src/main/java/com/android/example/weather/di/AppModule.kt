

package com.android.example.weather.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.android.example.weather.BuildConfig
import com.android.example.weather.R
import com.android.example.weather.data.db.WeatherDb
import com.android.example.weather.data.db.WeatherDao
import com.android.example.weather.data.network.WeatherService
import com.android.example.weather.data.network.QueryParameterAddInterceptor
import com.android.example.weather.usecase.weatherbycity.IWeatherByCityUseCase
import com.android.example.weather.usecase.weatherbycity.WeatherByCityUseCase
import com.android.example.weather.usecase.weatherbylatlon.IWeatherByLatLonUseCase
import com.android.example.weather.usecase.weatherbylatlon.WeatherByLatLonUseCase
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
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDb): WeatherDao {
        return db.weatherDao()
    }

    @Singleton
    @Provides
    fun provideWeatherByCityUseCase(weatherByCityUseCase: WeatherByCityUseCase): IWeatherByCityUseCase {
        return WeatherByCityUseCase(provideWeatherService())
    }

    @Singleton
    @Provides
    fun provideWeatherByLatLonUseCase(weatherByLatLonUseCase: WeatherByLatLonUseCase): IWeatherByLatLonUseCase {
        return WeatherByLatLonUseCase(provideWeatherService())
    }


}
