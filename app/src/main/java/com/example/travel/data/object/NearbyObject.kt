package com.example.travel.data.`object`

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel.data.model.Nearby.nearby
import com.example.travel.data.model.PlaceDetails.PlaceDetails
import com.example.travel.data.repository.NearbyApi
import kotlinx.coroutines.launch

class NearbyObject(lat: String?=null, lng: String?=null): ViewModel() {
    var nearbyResult:nearby? by mutableStateOf(nearby())

    init {
        getNearby(lat, lng)
    }

    fun getNearby(lat: String?=null, lng: String?=null){
        viewModelScope.launch {
            nearbyResult = lat?.let {
                NearbyApi.retrofitService.getNearby(
                    lat = it.toString(),
                    lng = lng.toString(),
                    query = "khách sạn",
                )
            }
        }
    }
}