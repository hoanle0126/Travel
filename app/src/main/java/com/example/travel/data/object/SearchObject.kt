package com.example.travel.data.`object`

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel.data.model.PlacesList.PlacesList
import com.example.travel.data.repository.PlaceApi
import kotlinx.coroutines.launch

class SearchObject(query: String?=null): ViewModel() {
    var searchResult:PlacesList? by mutableStateOf(PlacesList())

    init {
        getSearch(query)
    }

    fun getSearch(query:String?=null){
        viewModelScope.launch {
            searchResult = query?.let {
                PlaceApi.retrofitService.getPlaces(
                    query = it
                )
            }
        }
    }
}