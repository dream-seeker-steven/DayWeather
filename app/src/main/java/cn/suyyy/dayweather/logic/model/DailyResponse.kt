package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

class DailyResponse(val code: String, val updateTime: Date, @SerializedName("daily") val dailyList: List<Daily>) {
    class Daily(
        val fxDate: String,
        val sunrise: String,
        val sunset: String,
        val moonrise: String,
        val moonset: String,
        val moonPhase: String,
        val tempMax: String,
        val tempMin: String,
        val iconDay: String,
        val textDay: String,
        val iconNight: String,
        val textNight: String,
        val wind360Day: String,
        val windDirDay: String,
        val windScaleDay: String,
        val windSpeedDay: String,
        val wind360Night: String,
        val windDirNight: String,
        val windScaleNight: String,
        val windSpeedNight: String,
        val humidity: String,
        val precip: String,
        val pressure: String,
        val vis: String,
        val cloud: String,
        val uvIndex: String
    )
}

/*
* {
    "code": "200",
    "updateTime": "2020-11-25T20:35+08:00",
    "fxLink": "http://hfx.link/32t1",
    "daily": [
        {
            "fxDate": "2020-11-25",
            "sunrise": "06:34",
            "sunset": "16:58",
            "moonrise": "14:20",
            "moonset": "01:44",
            "moonPhase": "盈凸月",
            "tempMax": "12",
            "tempMin": "10",
            "iconDay": "305",
            "textDay": "小雨",
            "iconNight": "305",
            "textNight": "小雨",
            "wind360Day": "358",
            "windDirDay": "北风",
            "windScaleDay": "3-4",
            "windSpeedDay": "15",
            "wind360Night": "315",
            "windDirNight": "西北风",
            "windScaleNight": "3-4",
            "windSpeedNight": "16",
            "humidity": "87",
            "precip": "3.8",
            "pressure": "1024",
            "vis": "24",
            "cloud": "72",
            "uvIndex": "1"
        }
  }
* */

/*
*
daily.fxDate	预报日期	2013-05-31
daily.sunrise	日出时间	07:34
daily.sunset	日落时间	17:21
daily.moonrise	月升时间	16:09
daily.moonset	月落时间	04:21
daily.moonPhase	月相名称	满月
daily.tempMax	预报当天最高温度	4
daily.tempMin	预报当天最低温度	-5
daily.iconDay	预报白天天气状况的图标代码，图标可通过天气状况和图标下载	100
daily.textDay	预报白天天气状况文字描述，包括阴晴雨雪等天气状态的描述	晴
daily.iconNight	预报夜间天气状况的图标代码，图标可通过天气状况和图标下载	100
daily.textNight	预报晚间天气状况文字描述，包括阴晴雨雪等天气状态的描述	晴
daily.wind360Day	预报白天风向360角度	305
daily.windDirDay	预报白天风向	西北
daily.windScaleDay	预报白天风力等级	3-4
daily.windSpeedDay	预报白天风速，公里/小时	15
daily.wind360Night	预报夜间风向360角度	305
daily.WindDirNight	预报夜间当天风向	西北
daily.windScaleNight	预报夜间风力等级	3-4
daily.windSpeedNight	预报夜间风速，公里/小时	15
daily.humidity	预报当天相对湿度，百分比数值	40
daily.precip	预报当天降水量，默认单位：毫米	1.2
daily.pressure	预报当天大气压强，默认单位：百帕	1020
daily.vis	预报当天能见度，默认单位：公里	10
daily.cloud	预报当天云量，百分比数值	23
daily.uvIndex	预报当天紫外线强度指数
* */