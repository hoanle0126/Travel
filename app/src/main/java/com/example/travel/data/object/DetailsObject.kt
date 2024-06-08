package com.example.travel.data.`object`

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel.data.model.PlaceDetails.PlaceDetails
import com.example.travel.data.repository.DetailsApi
import kotlinx.coroutines.launch

class DetailsObject(id: String?=null): ViewModel() {
    var detailsResult:PlaceDetails? by mutableStateOf(PlaceDetails())

    init {
        getDetails(id)
    }

    fun getDetails(id:String?=null){
        viewModelScope.launch {
            detailsResult = DetailsApi.retrofitService.getDetails(id)
        }
    }
}