package ru.pavelapk.currency_rates.api

import retrofit2.http.GET

interface ICurrency {

    @GET("daily_json.js")
    suspend fun getDaily(): DailyResponse
}