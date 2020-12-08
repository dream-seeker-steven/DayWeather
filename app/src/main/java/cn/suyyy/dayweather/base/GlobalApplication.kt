package cn.suyyy.dayweather.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/*
* 全局Application
* */

class GlobalApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}