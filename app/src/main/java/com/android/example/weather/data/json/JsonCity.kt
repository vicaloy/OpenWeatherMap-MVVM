package com.android.example.weather.data.json

import android.content.Context
import com.android.example.weather.data.network.RequestCompleteListener
import com.android.example.weather.vo.City
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.IOException
import javax.inject.Inject

class JsonCity@Inject constructor(private val context: Context){
    fun getCitiesList(callback: RequestCompleteListener<List<City>>) {
        try {
            val stream = context.assets.open("cities_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val citiesList: List<City> = gson.fromJson(tContents, groupListType)

            citiesList.sortedBy { it.name }
            callback.onRequestSuccess(citiesList)

        } catch (e: IOException) {
            callback.onRequestFailed(requireNotNull(e.localizedMessage))
        }
    }
}