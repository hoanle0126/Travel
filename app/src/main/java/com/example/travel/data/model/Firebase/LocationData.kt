package com.example.travel.data.model.Firebase

data class LocationData(
    val name: String = "",
    val full_address: String = "",
    val date: String? = null,
    val time: String? = null,
    val user: String = "",
    var id: String = "KEY"
)