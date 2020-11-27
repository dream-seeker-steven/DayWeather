package cn.suyyy.dayweather.logic.common

import cn.suyyy.dayweather.constants.CoreConstant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherServiceCreator {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(CoreConstant.WEATHER_URL)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * inline 的工作原理就是将内联函数的函数体复制到调用处实现内联。
     * 在inline函数中我们可以指定类型不被擦除， 因为inline函数在编译期会将字节码copy到调用它的方法里，
     * 所以编译器会知道当前的方法中泛型对应的具体类型是什么，然后把泛型替换为具体类型，从而达到不被擦除的
     * 目的，在inline函数中我们可以通过reified关键字来标记这个泛型在编译时替换成具体类型
     */
    inline fun <reified T> create(): T = create(T::class.java)
}