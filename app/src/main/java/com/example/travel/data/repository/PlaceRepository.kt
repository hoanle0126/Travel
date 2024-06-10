package com.example.travel.data.repository

import com.example.travel.data.model.PlacesList.PlacesList
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://maps-data.p.rapidapi.com"

val client = OkHttpClient.Builder()
    .connectTimeout(100, TimeUnit.SECONDS)
    .readTimeout(100,TimeUnit.SECONDS)
    .addInterceptor{
        val request:Request = it.request().newBuilder()
            .addHeader("X-RapidAPI-Key", token)
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


interface PlaceRepository {
    @GET("/searchmaps.php")
    suspend fun getPlaces(
        @Query("query") query: String = "",
        @Query("limit") limit: Int = 12,
        @Query("offset") offset: Int = 0,
        @Query("lang") lang: String = "vietnamese",
        @Query("country") country: String = "vietnam",
        @Query("zoom") zoom: String = "13",
        @Query("lat") lat: String = "15.9031",
    ): PlacesList? = null
}

object PlaceApi {
    val retrofitService: PlaceRepository by lazy {
        retrofit.create(PlaceRepository::class.java)
    }
}