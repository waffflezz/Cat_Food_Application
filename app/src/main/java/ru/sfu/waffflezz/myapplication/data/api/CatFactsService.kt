package ru.sfu.waffflezz.myapplication.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CatFactsService {
    @GET("fact")
    suspend fun getFact(
    ): CatFact
}

object RetrofitInstance {
    private const val BASE_URL = "https://catfact.ninja/"

    val api: CatFactsService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatFactsService::class.java)
    }
}