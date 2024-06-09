package com.example.travel.data.repository

import com.example.travel.data.model.reviews.reviews
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://maps-data.p.rapidapi.com"

val client4 = OkHttpClient.Builder()
    .connectTimeout(100, TimeUnit.SECONDS)
    .readTimeout(100, TimeUnit.SECONDS)
    .addInterceptor{
        val request: Request = it.request().newBuilder()
            .addHeader("X-RapidAPI-Key", "54b12c1bd0mshc107b6188b97781p199c35jsncfb73a628b1e")
            .addHeader("X-RapidAPI-Host", "maps-data.p.rapidapi.com")
            .build()
        it.proceed(request)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client4)
    .build()


interface ReviewsRepository {
    @GET("/reviews.php")
    suspend fun getReviews(
        @Query("business_id") business_id: String? = "",
    ): reviews? = null
}

object ReviewsApi {
    val retrofitService: ReviewsRepository by lazy {
        retrofit.create(ReviewsRepository::class.java)
    }
}