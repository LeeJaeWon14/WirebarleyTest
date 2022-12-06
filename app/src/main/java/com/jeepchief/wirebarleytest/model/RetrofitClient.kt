package com.jeepchief.wirebarleytest.model

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroClient {
    private var instance: Retrofit? = null
    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder().addHeader(
                    "apikey",
                    "edj6OvmhXIGW3bBgjwCeycx1aUWWEJkg"
                ).build()
                return chain.proceed(request)
            }
        })
    }

    @Synchronized
    fun getInstance() : Retrofit {
        instance?.let {
            return it
        } ?: run {
            instance = Retrofit.Builder().apply {
                baseUrl("https://api.apilayer.com/currency_data/")
                addConverterFactory(GsonConverterFactory.create())
                client(httpClient.build())
            }.build()
            return instance!!
        }
    }
}