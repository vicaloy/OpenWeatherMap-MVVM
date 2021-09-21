

package com.android.example.weather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.android.example.weather.presentation.weather.WeatherViewModel
import com.android.example.weather.presentation.register.RegisterViewModel
import com.android.example.weather.presentation.viewmodel.WeatherViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(weatherViewModel: WeatherViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: WeatherViewModelFactory): ViewModelProvider.Factory
}
