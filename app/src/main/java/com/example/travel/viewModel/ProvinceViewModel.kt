package com.example.travel.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travel.model.ProvinceResult
import com.example.travel.repository.ProvinceApi
import kotlinx.coroutines.launch

class ProvinceViewModel:ViewModel() {
    var provinceResult:ProvinceResult by mutableStateOf(ProvinceResult())

    init {
        getResult()
    }

    private fun getResult(){
        viewModelScope.launch {
            provinceResult = ProvinceApi.retrofitService.getProvince()
        }
    }
}