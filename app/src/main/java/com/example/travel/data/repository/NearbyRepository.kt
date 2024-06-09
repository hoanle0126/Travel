package com.example.travel.data.repository

import com.example.travel.data.model.Nearby.nearby
import com.example.travel.data.model.PlaceDetails.PlaceDetails
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://maps-data.p.rapidapi.com"

val client3 = OkHttpClient.Builder()
    .connectTimeout(100, TimeUnit.SECONDS)
    .readTimeout(100, TimeUnit.SECONDS)
    .addInterceptor{
        val request: Request = it.request().newBuilder()
            .addHeader("X-RapidAPI-Key", "622337b20amsh77c2842aa285727p143b72jsnc6d8ac1c8957")
            .addHeader("X-RapidAPI-Host", "maps-data.p.rapidapi.com")
            .build()
        it.proceed(request)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client3)
    .build()


interface NearbyRepository {
    @GET("/nearby.php")
    suspend fun getNearby(
        @Query("lat") lat: String = "",
        @Query("lng") lng: String = "",
        @Query("query") query: String? = "",
        @Query("country") country: String = "vietnam",
    ): nearby? = null
}

object NearbyApi {
    val retrofitService: NearbyRepository by lazy {
        retrofit.create(NearbyRepository::class.java)
    }
}