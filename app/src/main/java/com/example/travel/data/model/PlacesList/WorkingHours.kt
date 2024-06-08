package com.example.travel.data.model.PlacesList

data class WorkingHours(
    val Friday: List<String> = emptyList(),
    val Monday: List<String> = emptyList(),
    val Saturday: List<String> = emptyList(),
    val Sunday: List<String> = emptyList(),
    val Thursday: List<String> = emptyList(),
    val Tuesday: List<String> = emptyList(),
    val Wednesday: List<String> = emptyList()
)