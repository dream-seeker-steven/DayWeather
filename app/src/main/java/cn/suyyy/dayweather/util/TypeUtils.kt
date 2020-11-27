package cn.suyyy.dayweather.util

object TypeUtils {

    fun getRainType(type: String) = when(type){
        "rain" -> "雨"
        "snow" -> "雪"
        else -> ""
    }
}