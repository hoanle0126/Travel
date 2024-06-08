package com.example.travel.data.`object`

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel.data.model.PlaceDetails.PlaceDetails
import com.example.travel.data.model.reviews.reviews
import com.example.travel.data.repository.DetailsApi
import com.example.travel.data.repository.ReviewsApi
import kotlinx.coroutines.launch

class ReviewsObject(id: String?=null): ViewModel() {
    var reviewsResult: reviews? by mutableStateOf(reviews(data = null))

    init {
        getReviews(id)
    }

    fun getReviews(id:String?=null){
        viewModelScope.launch {
            reviewsResult = ReviewsApi.retrofitService.getReviews(id)
        }
    }
}