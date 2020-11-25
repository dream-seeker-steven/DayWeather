package cn.suyyy.dayweather.logic.network

import cn.suyyy.dayweather.logic.common.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


object GlobalNetwork {

    private val cityService = ServiceCreator.create<CityService>()

    suspend fun searchCityList(query: String) = cityService.searchCityList(query).await()
    suspend fun getHotCityList() = cityService.getHotCityList().await()

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