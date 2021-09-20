package com.android.example.github.presentation.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.github.data.network.RequestCompleteListener
import com.android.example.github.data.repository.WeatherRepository
import com.android.example.github.vo.CurrentWeather
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> = _logoutSuccess

    fun loadWeather(cityId: Int) {

        repository.loadWeather(cityId, object :
            RequestCompleteListener<CurrentWeather> {
            override fun onRequestSuccess(data: CurrentWeather) {
                //live data con data: currentWeather
            }

            override fun onRequestFailed(errorMessage: String) {
                //callback.onRequestFailed(errorMessage)
                //livedata
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
}
