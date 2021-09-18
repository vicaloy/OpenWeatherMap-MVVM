package com.android.example.github.data.network

import com.android.example.github.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class QueryParameterAddInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url().newBuilder()
                .addQueryParameter("appid", BuildConfig.API_KEY)
                .build()

        val request = chain.request().newBuilder()
                // .addHeader("Authorization", "Bearer token")
                .url(url)
                .build()

        return chain.proceed(request)
    }
}