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

class VHLObject: ViewModel() {
    var nearbyResult:nearby? by mutableStateOf(nearby())

    init {
        getNearby()
    }

    fun getNearby(){
        viewModelScope.launch {
            nearbyResult =
                NearbyApi.retrofitService.getNearby(
                    lat = "20.927719",
                    lng = "107.183389",
                    query = "địa danh",
                )

        }
    }
}