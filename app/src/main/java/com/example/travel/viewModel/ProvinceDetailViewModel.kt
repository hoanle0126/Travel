package com.example.travel.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel.model.provinceDetail.ProvinceDetail
import com.example.travel.repository.ProvinceDetailApi
import kotlinx.coroutines.launch

class ProvinceDetailViewModel:ViewModel() {
    var provinceDetailResult:ProvinceDetail? by mutableStateOf(ProvinceDetail())

    init {
        getProvince()
    }

    private fun getProvince(){
        viewModelScope.launch {
            provinceDetailResult = ProvinceDetailApi.retrofitService.getDetail()
        }
    }
}