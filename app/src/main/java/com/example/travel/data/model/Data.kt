package com.example.travel.data.model

data class Data(
    val business_id: String,
    val city: String,
    val description: List<String>,
    val full_address: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val phone_number: String,
    val photos: List<Photo>,
    val place_id: String,
    val place_link: String,
    val price_level: Any,
    val rating: Double,
    val review_count: Int,
    val state: Any,
    val timezone: String,
    val types: List<String>,
    val verified: Boolean,
    val website: String,
    val working_hours: List<Any>
)