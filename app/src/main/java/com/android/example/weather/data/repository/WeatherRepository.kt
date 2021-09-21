package com.android.example.weather.data.repository

import com.android.example.weather.R
import com.android.example.weather.data.network.RequestCompleteListener
import com.android.example.weather.data.network.WeatherInfoResponse
import com.android.example.weather.data.db.WeatherDao
import com.android.example.weather.data.json.JsonCity
import com.android.example.weather.data.prefs.Prefs
import com.android.example.weather.usecase.weatherbycity.WeatherByCityUseCase
import com.android.example.weather.usecase.weatherbylatlon.WeatherByLatLonUseCase
import com.android.example.weather.util.format
import com.android.example.weather.util.kelvinToCelsius
import com.android.example.weather.vo.City
import com.android.example.weather.vo.CurrentWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepository @Inject constructor(
    private val weatherByLatLon: WeatherByLatLonUseCase,
    private val weatherByCity: WeatherByCityUseCase,
    private val weatherDao: WeatherDao,
    private val prefs: Prefs,
    private val jsonCity: JsonCity
) {

    fun getWeatherByCity(cityId: Int, callback: RequestCompleteListener<CurrentWeather>) {
        weatherByCity.getWeatherByCity(cityId, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                CoroutineScope(Dispatchers.IO).launch {

                    try {
                        val res = when (data.weather[0].main) {
                            "Thunderstorm" -> R.drawable.lightning_bolt
                            "Drizzle" -> R.drawable.lightning_bolt
                            "Rain" -> R.drawable.rain
                            "Snow" -> R.drawable.snow
                            "Clear" -> R.drawable.sun
                            "Clouds" -> R.drawable.clouds
                            else -> R.drawable.partly_cloudy_day
                        }
                        val currentWeather = CurrentWeather(
                            data.id,
                            data.main.temp.kelvinToCelsius().toString(),
                            "Pressure: ${data.main.pressure}",
                            "Humidity: ${data.main.humidity} %",
                            "Wind: ${data.wind.speed} km/h",
                            res,
                            "${data.name}, ${data.sys.country}",
                            Date().format()
                        )


                        weatherDao.insert(currentWeather)

                        callback.onRequestSuccess(currentWeather)
                    } catch (ex: Exception) {
                        callback.onRequestFailed(ex.localizedMessage)
                    }
                }

            }

            override fun onRequestFailed(errorMessage: String) {
                callback.onRequestFailed(errorMessage)
            }
        })
    }

    fun logout(callback: RequestCompleteListener<Boolean>) {
        prefs.logout(callback)
    }

    fun getCitiesList(callback: RequestCompleteListener<List<City>>) {
        jsonCity.getCitiesList(callback)
    }

    fun loadWeathersFromDb(cityId: Int, callback: RequestCompleteListener<CurrentWeather?>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                callback.onRequestSuccess(weatherDao.load(cityId))
            } catch (ex: Exception) {
                callback.onRequestFailed(ex.localizedMessage)
            }
        }
    }

    fun getWeatherByLatLon(lat:String, lon:String, callback: RequestCompleteListener<CurrentWeather>) {
        weatherByLatLon.getWeatherByLatLon(lat, lon, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                CoroutineScope(Dispatchers.IO).launch {

                    try {
                        val res = when (data.weather[0].main) {
                            "Thunderstorm" -> R.drawable.lightning_bolt
                            "Drizzle" -> R.drawable.lightning_bolt
                            "Rain" -> R.drawable.rain
                            "Snow" -> R.drawable.snow
                            "Clear" -> R.drawable.sun
                            "Clouds" -> R.drawable.clouds
                            else -> R.drawable.partly_cloudy_day
                        }
                        val currentWeather = CurrentWeather(
                            data.id,
                            data.main.temp.kelvinToCelsius().toString(),
                            "Pressure: ${data.main.pressure}",
                            "Humidity: ${data.main.humidity} %",
                            "Wind: ${data.wind.speed} km/h",
                            res,
                            "${data.name}, ${data.sys.country}",
                            Date().format()
                        )


                        weatherDao.insert(currentWeather)

                        callback.onRequestSuccess(currentWeather)
                    } catch (ex: Exception) {
                        callback.onRequestFailed(ex.localizedMessage)
                    }
                }

            }

            override fun onRequestFailed(errorMessage: String) {
                callback.onRequestFailed(errorMessage)
            }
        })
    }

}
