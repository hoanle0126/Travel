package com.example.travel.data.model.reviews

data class Review(
    val review_photos: List<String>?,
    val review_rate: Double?,
    val review_text: String?,
    val review_time: String?,
    val user_avatar: String?,
    val user_name: String?
)