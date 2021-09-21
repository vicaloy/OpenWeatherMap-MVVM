package com.android.example.weather.presentation.weather

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.weather.data.network.RequestCompleteListener
import com.android.example.weather.data.repository.WeatherRepository
import com.android.example.weather.util.wifiConnection
import com.android.example.weather.vo.City
import com.android.example.weather.vo.CurrentWeather
import java.text.DateFormat
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val context: Context,
    private val repository: WeatherRepository
) : ViewModel() {

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> = _logoutSuccess

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    private val _citiesList = MutableLiveData<List<City>>()
    val citiesList: LiveData<List<City>> = _citiesList


    fun getWeatherByCity(cityId: Int) {

        if (wifiConnection(context)) {
            repository.getWeatherByCity(cityId, object :
                RequestCompleteListener<CurrentWeather> {
                override fun onRequestSuccess(data: CurrentWeather) {
                    val mainLooper = Looper.getMainLooper()
                    Handler(mainLooper).post {
                        _currentWeather.value = data
                    }

                }

                override fun onRequestFailed(errorMessage: String) {
                    val mainLooper = Looper.getMainLooper()
                    Handler(mainLooper).post {
                        _currentWeather.value = null
                    }
                }
            })
        } else {
            repository.loadWeathersFromDb(cityId, object :
                RequestCompleteListener<CurrentWeather?> {
                override fun onRequestSuccess(data: CurrentWeather?) {
                    val mainLooper = Looper.getMainLooper()
                    Handler(mainLooper).post {

                        _currentWeather.value = data
                    }

                }

                override fun onRequestFailed(errorMessage: String) {
                    val mainLooper = Looper.getMainLooper()
                    Handler(mainLooper).post {
                        _currentWeather.value = null
                    }
                }
            })
        }


    }

    fun getWeatherByLatLon(lat: String, lon: String) {
        repository.getWeatherByLatLon(lat, lon, object :
            RequestCompleteListener<CurrentWeather> {
            override fun onRequestSuccess(data: CurrentWeather) {
                val mainLooper = Looper.getMainLooper()
                Handler(mainLooper).post {
                    _currentWeather.value = data
                }

            }

            override fun onRequestFailed(errorMessage: String) {
                val mainLooper = Looper.getMainLooper()
                Handler(mainLooper).post {
                    _currentWeather.value = null
                }
            }
        })

    }

    fun logout() {
        repository.logout(
            object :
                RequestCompleteListener<Boolean> {
                override fun onRequestSuccess(data: Boolean) {
                    _logoutSuccess.value = data
                }

                override fun onRequestFailed(errorMessage: String) {
                    _logoutSuccess.value = false
                }
            })
    }

    fun getCitiesList() {
        repository.getCitiesList(object : RequestCompleteListener<List<City>> {
            override fun onRequestSuccess(data: List<City>) {
                _citiesList.value = data
            }

            override fun onRequestFailed(errorMessage: String) {
                _citiesList.value = null
            }

        })
    }
}
