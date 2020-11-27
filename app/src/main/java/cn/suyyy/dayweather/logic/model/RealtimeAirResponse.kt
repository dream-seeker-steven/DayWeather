package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

class RealtimeAirResponse(val code: String, val updateTime: Date, @SerializedName("now") val realtimeAir: RealtimeAir ) {
    class RealtimeAir(
        val pubTime: Date,
        val aqi: String,
        val level: String,
        val category: String,
        val primary: String,
        val pm10: String,
        val pm2p5: String,
        val no2: String,
        val so2: String,
        val co: String,
        val o3: String
    )
}

/*
* {
    "code": "200",
    "updateTime": "2020-11-25T21:52+08:00",
    "fxLink": "http://hfx.link/32t4",
    "now": {
        "pubTime": "2020-11-25T21:00+08:00",
        "aqi": "39",
        "level": "1",
        "category": "优",
        "primary": "NA",
        "pm10": "36",
        "pm2p5": "27",
        "no2": "56",
        "so2": "5",
        "co": "0.9",
        "o3": "3"
    },
    "refer": {
        "sources": [
            "cnemc"
        ],
        "license": [
            "no commercial use"
        ]
    }
}
* */

/*
now.pubTime	实时空气质量数据发布时间	2013-12-30T01:45+08:00
now.aqi	实时空气质量指数	74
now.level	实时空气质量指数等级	2
now.category	实时空气质量指数级别	良
now.primary	实时空气质量的主要污染物，空气质量为优时，返回值为NA	pm2.5
now.pm10	实时 pm10	78
now.pm2p5	实时 pm2.5	66
now.no2	实时 二氧化氮	40
now.so2	实时 二氧化硫	30
now.co	实时 一氧化碳	33
now.o3	实时 臭氧
* */