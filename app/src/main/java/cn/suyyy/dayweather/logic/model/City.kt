package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName

class City(
    val name: String,
    val lon: String,
    val lat: String,
    @SerializedName("adm1") val province: String,
    @SerializedName("adm2") val city: String,
    val country: String
)

