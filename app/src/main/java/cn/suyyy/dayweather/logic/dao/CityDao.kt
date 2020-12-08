package cn.suyyy.dayweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import cn.suyyy.dayweather.base.GlobalApplication
import cn.suyyy.dayweather.logic.model.City
import com.google.gson.Gson

/**
 * 本地策略
 */

object CityDao {

    /**
     * 保存城市信息
     */
    fun saveCity(city: City): Boolean {
        sharedPreferences().edit() {
            putString("city", Gson().toJson(city))
        }
        return true
    }

    /**
     * 从存储中获取城市信息
     */
    fun getSaveCity(): City {
        val cityJson = sharedPreferences().getString("city", "")
        return Gson().fromJson(cityJson, City::class.java)
    }

    /**
     * 是否已经储存
     */
    fun isCitySaved() = sharedPreferences().contains("city")

    private fun sharedPreferences() =
        GlobalApplication.context.getSharedPreferences("day_weather", Context.MODE_PRIVATE)
}