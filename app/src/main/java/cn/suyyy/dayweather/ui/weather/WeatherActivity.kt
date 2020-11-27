package cn.suyyy.dayweather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.suyyy.dayweather.R
import cn.suyyy.dayweather.logic.model.Weather
import cn.suyyy.dayweather.util.IconUtils
import cn.suyyy.dayweather.util.TypeUtils
import cn.suyyy.dayweather.util.showToast
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.daily.*
import kotlinx.android.synthetic.main.minutely.*
import kotlinx.android.synthetic.main.realtime.*
import kotlinx.android.synthetic.main.realtime_air.*
import kotlinx.android.synthetic.main.realtime_hourly.*
import kotlinx.android.synthetic.main.realtime_indices.*
import kotlinx.android.synthetic.main.realtime_sun.*
import kotlinx.android.synthetic.main.realtime_wind.*
import java.text.SimpleDateFormat
import java.util.*


class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        setSupportActionBar(weatherToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (viewModel.location.isEmpty()) {
            viewModel.location = intent.getStringExtra("city_location") ?: ""
        }
        if (viewModel.name.isEmpty()) {
            viewModel.name = intent.getStringExtra("city_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                // 加载数据
                showWeatherInfo(weather)
            } else {
                "无法成功获取天气信息".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing = false
        })

        // 下拉刷新
        swipeRefresh.setColorSchemeResources(R.color.primary)
        refreshWeather()
        viewModel.refreshWeather(viewModel.location)

        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cityMenu -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.setting -> {
                "敬请期待".showToast()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.location)
        swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        toolbarTile.text = viewModel.name
        val realtimeWeather = weather.realtimeWeather

        // 填充realTime
        val realTimeTempValue = "${realtimeWeather.temp} ℃"
        realtimeTemp.text = realTimeTempValue
        realtimeWeatherText.text = realtimeWeather.text
        // 空气质量
        realtimeCategory.text = "空气${weather.realtimeAir.category}"

        // 清空以前数据
        dailyLayout.removeAllViews()
        // 填充daily_item.xml
        val dailyList = weather.dailyList
        for (i in dailyList.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.daily_item, dailyLayout, false)
            val dailyFxDate = view.findViewById(R.id.dailyFxDate) as TextView
            val dailySkyIcon = view.findViewById(R.id.dailySkyIcon) as ImageView
            val dailyText = view.findViewById(R.id.dailyText) as TextView
            val dailyTemp = view.findViewById(R.id.dailyTemp) as TextView
            dailyFxDate.text = dailyList[i].fxDate
            dailyText.text = dailyList[i].textDay
            dailySkyIcon.setImageResource(IconUtils.getDayIcon(dailyList[i].iconDay))
            dailyTemp.text = "${dailyList[i].tempMin} ℃ ~ ${dailyList[i].tempMax} ℃"
            dailyLayout.addView(view)
        }

        // 填充hourly
        hourlyLayout.removeAllViews()
        val hourlyList = weather.hourlyList
        for (i in hourlyList.indices) {
            val view = LayoutInflater.from(this)
                .inflate(R.layout.realtime_hourly_item, hourlyLayout, false)
            val hourlyFxTime = view.findViewById(R.id.hourlyFxTime) as TextView
            val hourlySkyIcon = view.findViewById(R.id.hourlySkyIcon) as ImageView
            val hourlyText = view.findViewById(R.id.hourlyText) as TextView
            val hourlyTemp = view.findViewById(R.id.hourlyTemp) as TextView

            hourlyFxTime.text = "${formatH(hourlyList[i].fxTime)} 时"
            hourlySkyIcon.setImageResource(IconUtils.getDayIcon(hourlyList[i].icon))
            hourlyText.text = hourlyList[i].text
            hourlyTemp.text = "${hourlyList[i].temp} ℃"
            hourlyLayout.addView(view)
        }

        // 填充minutely
        minutelyLayout.removeAllViews()
        val minutelyList = weather.minutelyList.minutelyList
        minutelySummary.text = weather.minutelyList.summary
        for (i in minutelyList.indices) {
            val view =
                LayoutInflater.from(this).inflate(R.layout.minutely_item, minutelyLayout, false)
            val minutelyFxTime = view.findViewById(R.id.minutelyFxTime) as TextView
            val minutelyText = view.findViewById(R.id.minutelyText) as TextView
            val minutelyPrecip = view.findViewById(R.id.minutelyPrecip) as TextView
            minutelyFxTime.text = formatHm(minutelyList[i].fxTime)
            minutelyText.text = TypeUtils.getRainType(minutelyList[i].type)
            minutelyPrecip.text = minutelyList[i].precip
            minutelyLayout.addView(view)
        }

        // 填充realtimeAir
        realtimeAirCategory.text = weather.realtimeAir.category
        realtimeAirAqi.text = "质量指数 ${weather.realtimeAir.aqi}"

        // 风和湿度
        realtimeWindDir.text = "风向\t${weather.realtimeWeather.windDir}"
        realtimeWindSpeed.text = "风速\t< ${weather.realtimeWeather.windSpeed}km/h"
        realtimeWindScale.text = "风力\t${weather.realtimeWeather.windScale}级"
        realtimeWindHumidity.text = "湿度\t${weather.realtimeWeather.humidity}%"
        realtimeWindVis.text = "能见度\t${weather.realtimeWeather.vis}km"

        // 日出日落
        realtimeSunrise.text = "日出 ${formatHm(weather.sunriseList.sunrise)}"
        realtimeSunset.text = "日落 ${formatHm(weather.sunriseList.sunset)}"

        // realtimeIndicesName realtimeIndicesCategory realtimeIndicesText
        // 填充minutely
        realtimeIndicesLLayout.removeAllViews()
        val realTimeIndicesList = weather.realTimeIndicesList
        for (i in realTimeIndicesList.indices) {
            val view = LayoutInflater.from(this)
                .inflate(R.layout.realtime_indices_item, realtimeIndicesLLayout, false)
            val realtimeIndicesName = view.findViewById(R.id.realtimeIndicesName) as TextView
            val realtimeIndicesCategory =
                view.findViewById(R.id.realtimeIndicesCategory) as TextView
            val realtimeIndicesText = view.findViewById(R.id.realtimeIndicesText) as TextView
            realtimeIndicesName.text = realTimeIndicesList[i].name
            realtimeIndicesCategory.text = realTimeIndicesList[i].category
            realtimeIndicesText.text = realTimeIndicesList[i].text
            realtimeIndicesLLayout.addView(view)
        }
    }

    private fun formatD(date: Date) = SimpleDateFormat("MM-dd", Locale.getDefault()).format(date)
    private fun formatH(date: Date) = SimpleDateFormat("HH", Locale.getDefault()).format(date)
    private fun formatm(date: Date) = SimpleDateFormat("mm", Locale.getDefault()).format(date)
    private fun formatHm(date: Date) = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)


}