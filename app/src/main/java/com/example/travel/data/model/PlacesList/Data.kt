package com.example.travel.data.model.PlacesList

data class Data(
    val business_id: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val name: String?,
    val photos: List<Photo>?,
    val rating: Double?,
    val types: List<String>?,
)