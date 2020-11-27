package cn.suyyy.dayweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.suyyy.dayweather.logic.repository.Repository

class WeatherViewModel : ViewModel(){
    private val locationLiveData = MutableLiveData<String>()

    var location = ""
    var name = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        Repository.refreshWeather(location)
    }

    fun refreshWeather(query: String){
        locationLiveData.value = query
    }
}