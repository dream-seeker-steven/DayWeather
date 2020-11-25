package cn.suyyy.dayweather.logic.repository

import androidx.lifecycle.liveData
import cn.suyyy.dayweather.logic.model.Location
import cn.suyyy.dayweather.logic.network.GlobalNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException


object Repository {

    fun searchCityList(query: String) = liveData(Dispatchers.IO) {
        val result = try{
            val cityResponse = GlobalNetwork.searchCityList(query)
            if (cityResponse.code == "200"){
                val locationList = cityResponse.locationList
                Result.success(locationList)
            }else{
                Result.failure(RuntimeException("response code is ${cityResponse.code}"))
            }
        }catch (e: Exception){
            Result.failure<List<Location>>(e)
        }
        emit(result)
    }
}