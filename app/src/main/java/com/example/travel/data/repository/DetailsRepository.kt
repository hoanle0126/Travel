package com.example.travel.data.repository

import com.example.travel.data.model.PlaceDetails.PlaceDetails
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://maps-data.p.rapidapi.com"

val client2 = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .addInterceptor{
        val request: Request = it.request().newBuilder()
            .addHeader("X-RapidAPI-Key", "31393214b8mshb27dfe97a14b1ebp1b9f16jsnccce8cc1fc49")
            .addHeader("X-RapidAPI-Host", "maps-data.p.rapidapi.com")
            .build()
        it.proceed(request)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client2)
    .build()


interface DetailsRepository {
    @GET("/place.php")
    suspend fun getDetails(
        @Query("business_id") business_id: String? = "",
        @Query("lang") lang: String = "vietnamese",
        @Query("country") country: String = "vietnam",
    ): PlaceDetails? = null
}

object DetailsApi {
    val retrofitService: DetailsRepository by lazy {
        retrofit.create(DetailsRepository::class.java)
    }
}