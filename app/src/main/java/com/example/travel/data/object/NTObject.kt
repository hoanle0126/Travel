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

class NTObject: ViewModel() {
    var nearbyResult:nearby? by mutableStateOf(nearby())

    init {
        getNearby()
    }

    fun getNearby(){
        viewModelScope.launch {
            nearbyResult =
                NearbyApi.retrofitService.getNearby(
                    lat = "12.243480",
                    lng = "109.196091",
                    query = "địa danh",
                )

        }
    }
}