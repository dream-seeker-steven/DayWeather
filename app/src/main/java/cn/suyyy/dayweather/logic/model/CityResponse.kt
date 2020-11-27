package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * 城市信息
 */

class CityResponse(val code: String, @SerializedName("location") val cityList: List<City>)

/*
{
    "code": "200",
    "location": [
    {
        "name": "南昌",
        "id": "101240101",
        "lat": "28.67649",
        "lon": "115.89215",
        "adm2": "南昌",
        "adm1": "江西省",
        "country": "中国",
        "tz": "Asia/Shanghai",
        "utcOffset": "+08:00",
        "isDst": "0",
        "type": "city",
        "rank": "11",
        "fxLink": "http://hfx.link/36z1"
    },
    ...
}
*/
