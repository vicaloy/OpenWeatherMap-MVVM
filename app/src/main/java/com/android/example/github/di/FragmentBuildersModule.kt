

package com.android.example.github.di

import com.android.example.github.presentation.weather.WeatherFragment
import com.android.example.github.presentation.register.RegisterFragment

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
