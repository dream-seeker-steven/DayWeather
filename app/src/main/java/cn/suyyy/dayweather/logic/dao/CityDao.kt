package cn.suyyy.dayweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import cn.suyyy.dayweather.base.GlobalApplication
import cn.suyyy.dayweather.logic.model.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CityDao {

    fun saveCity(city: City):Boolean {
        sharedPreferences().edit(){
            putString("city",Gson().toJson(city))
        }
        return true
    }

    fun getSaveCity() : City {
        val cityJson = sharedPreferences().getString("city", "")
        return Gson().fromJson(cityJson,City::class.java)
    }

    fun isCitySaved() = sharedPreferences().contains("city")

    private fun sharedPreferences() =
        GlobalApplication.context.getSharedPreferences("day_weather", Context.MODE_PRIVATE)
}