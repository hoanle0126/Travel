package com.example.travel.data.model.reviews

data class Review(
    val business_response_text: String,
    val review_cursor: String,
    val review_link: String,
    val review_photos: List<String>,
    val review_rate: Double,
    val review_text: String,
    val review_time: String,
    val review_timestamp: Long,
    val translations: Translations,
    val user_avatar: String,
    val user_name: String
)