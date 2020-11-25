package cn.suyyy.dayweather.logic.network

import cn.suyyy.dayweather.constants.CoreConstant
import cn.suyyy.dayweather.logic.common.ServiceCreator
import cn.suyyy.dayweather.logic.model.CityResponse
import cn.suyyy.dayweather.logic.model.HotCityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {

    // https://geoapi.qweather.com/v2/city/lookup?
    // https://geoapi.qweather.com/v2/city/top?

    @GET("${CoreConstant.CITY_URL}/v2/city/lookup?${CoreConstant.TOKEN}")
    fun searchCityList(@Query("location") query: String): Call<CityResponse>

    @GET("${CoreConstant.CITY_URL}/v2/city/top?${CoreConstant.TOKEN}&range=cn")
    fun getHotCityList(): Call<HotCityResponse>
}