package cn.suyyy.dayweather.logic.network

import cn.suyyy.dayweather.constants.CoreConstant
import cn.suyyy.dayweather.logic.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("v7/weather/now?key=${CoreConstant.TOKEN}")
    fun getRealTimeWeather(@Query("location") query: String): Call<RealtimeWeatherResponse>

    @GET("v7/weather/7d?key=${CoreConstant.TOKEN}")
    fun getDaily(@Query("location") query: String): Call<DailyResponse>

    @GET("v7/weather/24h?key=${CoreConstant.TOKEN}")
    fun getHourly(@Query("location")query: String): Call<HourlyResponse>

    @GET("v7/minutely/5m?key=${CoreConstant.TOKEN}")
    fun getMinutely(@Query("location")query: String): Call<MinutelyResponse>

    @GET("v7/air/now?key=${CoreConstant.TOKEN}")
    fun getRealtimeAir(@Query("location")query: String): Call<RealtimeAirResponse>

    @GET("v7/indices/1d?key=${CoreConstant.TOKEN}&type=0")
    fun getIndices(@Query("location")query: String): Call<RealTimeIndicesResponse>

    // yyyyMMdd
    @GET("v7/astronomy/sunmoon?key=${CoreConstant.TOKEN}")
    fun getSunrise(@Query("location")query: String, @Query("date") date: String): Call<SunriseResponse>
}