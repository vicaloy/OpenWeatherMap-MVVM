

package com.android.example.github.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.android.example.github.presentation.weather.WeatherViewModel
import com.android.example.github.presentation.register.RegisterViewModel
import com.android.example.github.presentation.viewmodel.WeatherViewModelFactory

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
