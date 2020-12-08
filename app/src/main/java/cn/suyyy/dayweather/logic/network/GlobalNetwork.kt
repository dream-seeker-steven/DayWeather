package cn.suyyy.dayweather.logic.network

import cn.suyyy.dayweather.logic.common.CityServiceCreator
import cn.suyyy.dayweather.logic.common.WeatherServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 使用协程优化请求
 */

object GlobalNetwork {

    private val cityService = CityServiceCreator.create<CityService>()
    private val weatherService = WeatherServiceCreator.create<WeatherService>()

    suspend fun searchCityList(query: String) = cityService.searchCityList(query).await()
    suspend fun getHotCityList() = cityService.getHotCityList().await()

    // 将网络请求挂起
    suspend fun getRealTimeWeather(query: String) = weatherService.getRealTimeWeather(query).await()
    suspend fun getDaily(query: String) = weatherService.getDaily(query).await()
    suspend fun getHourly(query: String) = weatherService.getHourly(query).await()
    suspend fun getMinutely(query: String) = weatherService.getMinutely(query).await()
    suspend fun getRealtimeAir(query: String) = weatherService.getRealtimeAir(query).await()
    suspend fun getIndices(query: String) = weatherService.getIndices(query).await()
    suspend fun getSunrise(query: String, date: String) =
        weatherService.getSunrise(query, date).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            // enqueue 发送异步请求，通过回调返回结果
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}