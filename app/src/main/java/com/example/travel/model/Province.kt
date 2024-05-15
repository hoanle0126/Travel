package com.example.travel.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProvinceResult(
    val error:Int = 0,
    @SerialName("error_text")
    val errorText:String = "",
    @SerialName("data_name")
    val dataName:String = "",
    val data:List<Province> = emptyList()
)

@Serializable
data class Province(
    val id:String,
    val name:String,
    @SerialName("name_en")
    val nameEn:String,
    @SerialName("full_name")
    val fullName:String,
    @SerialName("full_name_en")
    val fullNameEn:String,
    val latitude:Double,
    val longitude:Double
)