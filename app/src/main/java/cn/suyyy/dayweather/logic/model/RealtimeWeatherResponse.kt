package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

class RealtimeWeatherResponse(val code: String, val updateTime: Date, @SerializedName("now") val realtimeWeather: RealtimeWeather) {
    class RealtimeWeather(
        val obsTime: Date,
        val tempe: String,
        val feelsLike: String,
        val icon: String,
        val text: String,
        val wind360: String,
        val windDir: String,
        val windScale: String,
        val windSpeed: String,
        val humidity: String,
        val precip: String,
        val pressure: String,
        val vis: String,
        val cloud: String,
        val dew: String
    )
}

/*
* {
    "code": "200",
    "updateTime": "2020-11-25T21:26+08:00",
    "fxLink": "http://hfx.link/32t1",
    "now": {
        "obsTime": "2020-11-25T21:04+08:00",
        "temp": "12", 实况温度
        "feelsLike": "11", 体感温度
        "icon": "305",
        "text": "小雨",
        "wind360": "315",
        "windDir": "西北风",
        "windScale": "2",
        "windSpeed": "6",
        "humidity": "98",
        "precip": "0.1",
        "pressure": "1020",
        "vis": "2",
        "cloud": "100",
        "dew": "10"
    },
    "refer": {
        "sources": [
            "Weather China"
        ],
        "license": [
            "no commercial use"
        ]
    }
}
* */

/*
*
now.obsTime	实况观测时间	2013-12-30T01:45+08:00
now.temp	实况温度，默认单位：摄氏度	21
now.feelsLike	实况体感温度，默认单位：摄氏度	23
now.icon	当前天气状况和图标的代码，图标可通过天气状况和图标下载	100
now.text	实况天气状况的文字描述，包括阴晴雨雪等天气状态的描述	晴
now.wind360	实况风向360角度	305
now.windDir	实况风向	西北
now.windScale	实况风力等级	3
now.windSpeed	实况风速，公里/小时	15
now.humidity	实况相对湿度，百分比数值	40
now.precip	实况降水量，默认单位：毫米	1.2
now.pressure	实况大气压强，默认单位：百帕	1020
now.vis	实况能见度，默认单位：公里	10
now.cloud	实况云量，百分比数值	23
now.dew	实况露点温度
* */