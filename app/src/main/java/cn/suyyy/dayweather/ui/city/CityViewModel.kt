package cn.suyyy.dayweather.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.suyyy.dayweather.logic.model.City
import cn.suyyy.dayweather.logic.repository.Repository

class CityViewModel : ViewModel() {

    private val searchCityLiveData = MutableLiveData<String>()

    val cityList = ArrayList<City>()

    val cityLiveData = Transformations.switchMap(searchCityLiveData) { query ->
        Repository.searchCityList(query)
    }

    fun searchCityList(query: String) {
        searchCityLiveData.value = query
    }
}