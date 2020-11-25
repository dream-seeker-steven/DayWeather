package cn.suyyy.dayweather.util

import android.widget.Toast
import cn.suyyy.dayweather.base.GlobalApplication

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(GlobalApplication.context, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(GlobalApplication.context, this, duration).show()
}