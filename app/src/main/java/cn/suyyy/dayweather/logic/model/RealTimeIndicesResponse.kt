package cn.suyyy.dayweather.logic.model

import com.google.gson.annotations.SerializedName

class RealTimeIndicesResponse(
    val code: String,
    @SerializedName("daily") val indicesDailyList: List<IndicesDaily>
) {
    class IndicesDaily(
        val name: String,
        val category: String,
        val text: String
    )

}

/*
* {
    "code": "200",
    "updateTime": "2020-11-26T17:36+08:00",
    "fxLink": "http://hfx.link/32t2",
    "daily": [
        {
            "date": "2020-11-26",
            "type": "13",
            "name": "化妆指数",
            "level": "1",
            "category": "保湿",
            "text": "气温较低，风力较大，用滋润型化妆品，少扑粉，用润唇膏后再抹口红。"
        },
        }
* */

/*
code	API状态码，具体含义请参考状态码	200
updateTime	当前API的最近更新时间	2013-12-30T01:45+08:00
fxLink	该城市的天气生活指数自适应网页，可嵌入网站或应用	http://hfx.link/ae45
daily.date	预报日期	2018-05-30
daily.type	生活指数预报类型	2
daily.name	生活指数预报类型的名称	舒适度指数
daily.level	生活指数预报等级	1
daily.category	生活指数预报级别名称	舒适
daily.text	生活指数预报的详细描述，可能为空	白天温度适宜，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
refer.sources	原始数据来源，可能为空
refer.license	数据许可证
* */