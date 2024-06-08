package com.example.travel.data.model.reviews

data class Data(
    val business_id: String,
    val description: List<Any>,
    val full_address: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val phone_number: String,
    val place_id: String,
    val place_link: String,
    val price_level: String,
    val review_count: Int,
    val reviews: List<Review>,
    val state: String,
    val timezone: String,
    val types: List<String>,
    val website: String,
    val website_full: String,
)