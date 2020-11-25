package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName

class HotCityResponse(val code: String,  val topCityList: List<Location>)

/*
* {
    "code": "200",
    "topCityList": [
        {
            "name": "余杭",
            "id": "101210106",
            "lat": "30.42118",
            "lon": "120.30173",
            "adm2": "杭州",
            "adm1": "浙江省",
            "country": "中国",
            "tz": "Asia/Shanghai",
            "utcOffset": "+08:00",
            "isDst": "0",
            "type": "city",
            "rank": "25",
            "fxLink": "http://hfx.link/32t1"
        },
   }
* */