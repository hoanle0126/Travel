package com.example.travel.repository

import com.example.travel.model.ProvinceResult
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://esgoo.net/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface ProvinceRepository {
    @GET("api-tinhthanh/1/0.htm")
    suspend fun getProvince():ProvinceResult
}

object ProvinceApi {
    val retrofitService:ProvinceRepository by lazy {
        retrofit.create(ProvinceRepository::class.java)
    }
}