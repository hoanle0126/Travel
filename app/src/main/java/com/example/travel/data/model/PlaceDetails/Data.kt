package com.example.travel.data.model.PlaceDetails

data class Data(
    val business_id: String?,
    val full_address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val name: String?,
    val phone_number: String?,
    val photos: List<Photo>?,
    val rating: Double?,
    val review_count: Int?,
    val state: String?,
    val timezone: String?,
    val types: List<String>?,
    val working_hours: Any?,
    val city: String?
)