package cn.suyyy.dayweather.app

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cn.suyyy.dayweather.R
import cn.suyyy.dayweather.base.GlobalApplication
import cn.suyyy.dayweather.logic.model.City
import cn.suyyy.dayweather.ui.city.CityViewModel
import cn.suyyy.dayweather.ui.weather.WeatherActivity
import cn.suyyy.dayweather.util.showToast
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this).get(CityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isInternetAvailable(this)) {
            AlertDialog.Builder(this).apply {
                setTitle("提示")
                setMessage("未检测到网络，请检查！")
                    .setPositiveButton("检查网络") { dialog, which ->
                        finish()
                    }
            }.show()
        } else {
            if (!viewModel.isCitySaved()) {
                // 申请权限
                requestLocationPermissions()
                val location = getMapLocation()
                location.stopLocation()
                location.startLocation()
            }
            setContentView(R.layout.activity_main)
            setSupportActionBar(mainToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    @Suppress("DEPRECATION")
    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    private fun getMapLocation(): AMapLocationClient {
        val locationClient = AMapLocationClient(GlobalApplication.context)
        locationClient.setLocationListener(MyLocationListener(this))
        val option = AMapLocationClientOption()
        option.isOnceLocation = true
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        option.isNeedAddress = true
        locationClient.setLocationOption(option);
        //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
        return locationClient
    }


    private fun requestLocationPermissions() {
        val permissionList = ArrayList<String>()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE)
        }

        if (permissionList.isNotEmpty()) {
            val permissions = permissionList.toTypedArray()
            ActivityCompat.requestPermissions(this, permissions, 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty()) {
                    var temp = grantResults.size
                    for (result in grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            "你拒绝了授权定位相关权限，无法使用定位功能".showToast()
                        } else {
                            temp--
                        }
                    }
                    if (temp == 0) {
                        "正在定位".showToast()
                        val location = getMapLocation()
                        location.stopLocation()
                        location.startLocation()
                    }
                }
            }
        }
    }

    inner class MyLocationListener(val context: Context) : AMapLocationListener {
        override fun onLocationChanged(location: AMapLocation?) {
            if (location?.getErrorCode() == 0) {
                if (!viewModel.isCitySaved()) {
                    // 查到地点后的逻辑
                    AlertDialog.Builder(context).apply {
                        setTitle("提示")
                        setMessage("检测到您当前位置是 ${location.district} 是都立即切换城市")
                            .setPositiveButton("切换") { dialog, which ->
                                val intent = Intent(context, WeatherActivity::class.java).apply {
                                    val lola = "${location.longitude},${location.latitude}"
                                    putExtra("city_location", lola)
                                    putExtra("city_name", location.district)
                                }
                                val city = City(location.district,"${location.longitude}",
                                    "location.latitude",location.city,location.province,location.country)
                                viewModel.saveCity(city)
                                startActivity(intent)
                                finish()
                            }
                            .setNegativeButton("取消") { dialog, which ->
                            }
                    }.show()
                }
            }
        }
    }
}