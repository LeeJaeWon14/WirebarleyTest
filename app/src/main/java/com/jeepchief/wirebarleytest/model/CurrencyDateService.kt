package com.jeepchief.wirebarleytest.model

import retrofit2.http.GET

interface CurrencyDateService {
    @GET("live")
    suspend fun getLive() : LiveDTO
}