

package com.android.example.weather.di

import com.android.example.weather.presentation.weather.WeatherFragment
import com.android.example.weather.presentation.register.RegisterFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeWeatherFragment(): WeatherFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment
}
