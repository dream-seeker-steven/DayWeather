package cn.suyyy.dayweather.logic.repository

import androidx.lifecycle.liveData
import cn.suyyy.dayweather.logic.dao.CityDao
import cn.suyyy.dayweather.logic.model.City
import cn.suyyy.dayweather.logic.model.Weather
import cn.suyyy.dayweather.logic.network.GlobalNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * 中间层
 */

object Repository {

    fun searchCityList(query: String) = fire(Dispatchers.IO) {
        val cityResponse = GlobalNetwork.searchCityList(query)
        if (cityResponse.code == "200") {
            val cityList = cityResponse.cityList
            Result.success(cityList)
        } else {
            Result.failure(RuntimeException("response code is ${cityResponse.code}"))
        }
    }

    fun getHotCityList() = fire(Dispatchers.IO) {
        val hotCityResponse = GlobalNetwork.getHotCityList()
        if (hotCityResponse.code == "200") {
            val hotCityList = hotCityResponse.topCityList
            Result.success(hotCityList)
        } else {
            Result.failure(RuntimeException("response code is ${hotCityResponse.code}"))
        }
    }

    fun saveCity(city: City) =  CityDao.saveCity(city)

    fun getSaveCity() = CityDao.getSaveCity()

    fun isCitySaved() = CityDao.isCitySaved()

    fun refreshWeather(query: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealTime = async {
                GlobalNetwork.getRealTimeWeather(query)
            }
            val deferredDaily = async {
                GlobalNetwork.getDaily(query)
            }
            val deferredHourly = async {
                GlobalNetwork.getHourly(query)
            }
            val deferredMinutely = async {
                GlobalNetwork.getMinutely(query)
            }
            val deferredRealtimeAir = async {
                GlobalNetwork.getRealtimeAir(query)
            }
            val deferredIndices = async {
                GlobalNetwork.getIndices(query)
            }
            val deferredSunrise = async {
                GlobalNetwork.getSunrise(query, getNowDate())
            }
            val realtimeWeatherResponse = deferredRealTime.await()
            val dailyResponse = deferredDaily.await()
            val hourlyResponse = deferredHourly.await()
            val minutelyResponse = deferredMinutely.await()
            val realtimeAirResponse = deferredRealtimeAir.await()
            val indicesResponse = deferredIndices.await()
            val sunriseResponse = deferredSunrise.await()

            if (isSuccess(
                    realtimeWeatherResponse.code,
                    dailyResponse.code,
                    hourlyResponse.code,
                    minutelyResponse.code,
                    realtimeAirResponse.code,
                    indicesResponse.code,
                    sunriseResponse.code
                )
            ) {
                val weather =
                    Weather(
                        realtimeWeatherResponse.realtimeWeather,
                        dailyResponse.dailyList,
                        hourlyResponse.hourlyList,
                        minutelyResponse,
                        realtimeAirResponse.realtimeAir,
                        indicesResponse.indicesDailyList,
                        sunriseResponse
                    )
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtimeWeatherResponse code is ${realtimeWeatherResponse.code} \n"
                                + "dailyResponse code is ${dailyResponse.code} \n"
                                + "hourlyResponse code is ${hourlyResponse.code} \n"
                                + "minutelyResponse code is ${minutelyResponse.code} \n"
                                + "realtimeAirResponse code is ${realtimeAirResponse.code} \n"
                                + "indicesResponse code is ${indicesResponse.code} \n"
                                + "sunriseResponse code is ${sunriseResponse.code} \n"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: java.lang.Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    /**
     * 简单判断请求成功
     */
    private fun isSuccess(vararg codes: String): Boolean {
        for (code in codes) {
            if (code != "200") {
                return false
            }
        }
        return true
    }

    private fun getNowDate(): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return simpleDateFormat.format(Date())
    }
}