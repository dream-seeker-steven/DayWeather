package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName

class City(
    val name: String,
    val lat: String,
    val lon: String,
    @SerializedName("adm1") val province: String,
    @SerializedName("adm2") val city: String
)

