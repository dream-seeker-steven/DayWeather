package cn.suyyy.dayweather.logic.model

class Weather(
    val realtimeWeather: RealtimeWeatherResponse.RealtimeWeather,
    val dailyList: List<DailyResponse.Daily>,
    val hourlyList: List<HourlyResponse.Hourly>,
    val minutelyList: MinutelyResponse,
    val realtimeAir: RealtimeAirResponse.RealtimeAir,
    val realTimeIndicesList: List<RealTimeIndicesResponse.IndicesDaily>,
    val sunriseList: SunriseResponse
)