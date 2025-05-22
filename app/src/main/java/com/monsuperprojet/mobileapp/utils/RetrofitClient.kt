package com.monsuperprojet.mobileapp.utils

import com.monsuperprojet.mobileapp.api.ProduitApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.100.52:8087/"


    val instance: ProduitApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProduitApiService::class.java)
    }
}
