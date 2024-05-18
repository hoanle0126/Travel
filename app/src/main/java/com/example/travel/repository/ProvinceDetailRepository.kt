package com.example.travel.repository

import com.example.travel.model.provinceDetail.ProvinceDetail
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://maps-data.p.rapidapi.com"

val client = OkHttpClient.Builder()
    .connectTimeout(100,TimeUnit.SECONDS)
    .readTimeout(100,TimeUnit.SECONDS)
    .addInterceptor{
        val request:Request = it.request().newBuilder()
            .addHeader("X-RapidAPI-Key", "fc66525e53msha343d1b0e72d42bp124eb3jsnab449c4f57ea")
            .addHeader("X-RapidAPI-Host", "maps-data.p.rapidapi.com")
            .build()
        it.proceed(request)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

interface ProvinceDetailRepository {
    @GET("/searchmaps.php")
    suspend fun getDetail(
        @Query("query") query: String = "hotel"
    ): ProvinceDetail ? = null
}

object ProvinceDetailApi {
    val retrofitService:ProvinceDetailRepository by lazy {
        retrofit.create(ProvinceDetailRepository::class.java)
    }
}