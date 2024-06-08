package com.example.travel.data.`object`

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel.data.model.PlacesList.PlacesList
import com.example.travel.data.repository.PlaceApi
import kotlinx.coroutines.launch

class PlaceObject:ViewModel() {
    var placeResult:PlacesList? by mutableStateOf(PlacesList())

    init {
        getRestaurant()
    }

    private fun getRestaurant(){
        viewModelScope.launch {
            placeResult = PlaceApi.retrofitService.getPlaces(
                query = "khách sạn"
            )
        }
    }
}