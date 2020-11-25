package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

class HourlyResponse(val code: String, val updateTime: Date , @SerializedName("hourly") val hourlyList: List<Hourly>) {
    class Hourly(
        val fxTime: Date,
        val temp: String,
        val icon: String,
        val text: String,
        val wind360: String,
        val windDir: String,
        val windScale: String,
        val windSpeed: String,
        val humidity: String,
        val pop: String,
        val precip: String,
        val pressure: String,
        val cloud: String,
        val dew: String
    )
}

/*
* {
    "code": "200",
    "updateTime": "2020-11-25T21:35+08:00",
    "fxLink": "http://hfx.link/32t1",
    "hourly": [
        {
            "fxTime": "2020-11-25T23:00+08:00",
            "temp": "10",
            "icon": "101",
            "text": "多云",
            "wind360": "318",
            "windDir": "西北风",
            "windScale": "3-4",
            "windSpeed": "16",
            "humidity": "91",
            "pop": "49",
            "precip": "0.0",
            "pressure": "1025",
            "cloud": "100",
            "dew": "9"
        },
* */

/*
hourly.fxTime	逐小时预报时间	2013-12-30T13:00+08:00
hourly.temp	逐小时预报温度	2
hourly.icon	逐小时预报天气状况图标代码，图标可通过天气状况和图标下载	101
hourly.text	逐小时预报天气状况文字描述，包括阴晴雨雪等天气状态的描述	多云
hourly.wind360	逐小时预报风向360角度	305
hourly.windDir	逐小时预报风向	西北
hourly.windScale	逐小时预报风力等级	3
hourly.windSpeed	逐小时预报风速，公里/小时	15
hourly.humidity	逐小时预报相对湿度，百分比数值	40
hourly.precip	逐小时预报降水量，默认单位：毫米	1.2
hourly.pop	逐小时预报降水概率，百分比数值，可能为空	5
hourly.pressure	逐小时预报大气压强，默认单位：百帕	1020
hourly.cloud	逐小时预报云量，百分比数值	23
hourly.dew	逐小时预报露点温度
* */