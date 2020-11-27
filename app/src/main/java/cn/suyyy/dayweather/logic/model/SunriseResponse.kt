package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

class SunriseResponse(
    val code: String,
    val sunrise: Date,
    val sunset: Date,
    val moonrise: Date,
    val moonset: Date,
    @SerializedName("moonPhase") val moonPhaseList: List<MoonPhase>
) {
    class MoonPhase(
        val fxTime: Date,
        val name: String
    )
}

/*
* {
    "code": "200",
    "updateTime": "2020-11-26T18:00+08:00",
    "fxLink": "http://hfx.link/32t1",
    "sunrise": "2020-11-26T06:35+08:00",
    "sunset": "2020-11-26T16:57+08:00",
    "moonrise": "2020-11-26T14:48+08:00",
    "moonset": "2020-11-27T02:37+08:00",
    "moonPhase": [
        {
            "fxTime": "2020-11-26T00:00+08:00",
            "value": "0.36",
            "name": "盈凸月",
            "illumination": "81"
        },
        {
            "fxTime": "2020-11-26T01:00+08:00",
            "value": "0.36",
            "name": "盈凸月",
            "illumination": "81"
        },
        {
* */

/*
code	API状态码，具体含义请参考状态码	200
updateTime	当前API的最近更新时间	2013-12-30T01:45+08:00
fxLink	该城市的太阳和月亮自适应网页，可嵌入网站或应用	http://hfx.link/ae45
sunrise	日出时间	2013-12-30T05:44+08:00
sunset	日落时间	2013-12-30T17:02+08:00
moonrise	月升时间	2013-12-30T13:19+08:00
moonset	月落时间	2013-12-31T23:31+08:00
moonPhase.fxTime	月相逐小时预报时间	2013-12-31T23:31+08:00
moonPhase.value	月相数值	0.25
moonPhase.name	月相名字	上弦月
moonPhase.illumination	月亮照明度，百分比数值	30
refer.sources	原始数据来源，可能为空
refer.license	数据许可证
* */