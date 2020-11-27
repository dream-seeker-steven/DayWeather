package cn.suyyy.dayweather.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.suyyy.dayweather.logic.model.City
import cn.suyyy.dayweather.logic.repository.Repository

class CityViewModel : ViewModel() {

    private val searchCityLiveData = MutableLiveData<String>()
    private val getHotCityLiveData = MutableLiveData<String>()

    val cityList = ArrayList<City>()

    val cityLiveData = Transformations.switchMap(searchCityLiveData) { query ->
        Repository.searchCityList(query)
    }

    val hotCityLiveData = Transformations.switchMap(getHotCityLiveData) {
        Repository.getHotCityList()
    }

    fun searchCityList(query: String) {
        searchCityLiveData.value = query
    }

    fun getHotCityList() {
        getHotCityLiveData.value = ""
    }

    fun saveCity(city: City) = Repository.saveCity(city)

    fun getSaveCity() = Repository.getSaveCity()

    fun isCitySaved() = Repository.isCitySaved()

}