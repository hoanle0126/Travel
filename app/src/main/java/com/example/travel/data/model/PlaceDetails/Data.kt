package com.example.travel.data.model.PlaceDetails

data class Data(
    val business_id: String? = "",
    val full_address: String? ="",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val name: String? = "",
    val phone_number: String? = "",
    val photos: List<Photo>? = emptyList(),
    val rating: Double? = 0.0,
    val review_count: Int? = 0,
    val state: String? = "",
    val timezone: String? = "",
    val types: List<String>? = emptyList(),
    val working_hours: Any? = "",
    val city: String? = ""
)