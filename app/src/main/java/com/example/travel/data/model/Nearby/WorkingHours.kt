package com.example.travel.data.model.Nearby

data class WorkingHours(
    val Friday: List<String>,
    val Monday: List<String>,
    val Saturday: List<String>,
    val Sunday: List<String>,
    val Thursday: List<String>,
    val Tuesday: List<String>,
    val Wednesday: List<String>
)