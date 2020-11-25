package cn.suyyy.dayweather.logic.model

import java.util.*

class MinutelyResponse(val code: String, val updateTime: Date, val summary: String, val minutelyList: List<Minutely>) {
    class Minutely(
        val fxTime: String,
        val precip: String,
        val type: String
    )
}

/*
* {
    "code": "200",
    "updateTime": "2020-11-25T22:15+08:00",
    "fxLink": "http://hfx.link/1",
    "summary": "5分钟后开始下小雨",
    "minutely": [
        {
            "fxTime": "2020-11-25T22:15+08:00",
            "precip": "0.0",
            "type": "rain"
        },
        {
            "fxTime": "2020-11-25T22:20+08:00",
            "precip": "0.08",
            "type": "rain"
        },
* */

/*
code	API状态码，具体含义请参考状态码	200
updateTime	当前API的最近更新时间	2013-12-30T01:45+08:00
fxLink	该城市的分钟级降水自适应网页，可嵌入网站或应用。有可能为空。	http://hfx.link/ae45
summary	分钟降水描述	未来2小时无降雨
minutely.fxTime	预报时间	2013-12-30T01:45+08:00
minutely.precip	降水量	10
minutely.type	降水类型
rain雨
snow雪	rain
* */