package com.example.travel.data.model

import com.example.travel.data.model.PlaceDetails.Data
import com.example.travel.data.model.PlaceDetails.PlaceDetails
import kotlinx.serialization.Serializable

data class ToDo(
    val name: String = "",
    val email: String = "",
    val place: PlaceDetails = PlaceDetails(),
    val info: String = "",
    var id: String = ""
)