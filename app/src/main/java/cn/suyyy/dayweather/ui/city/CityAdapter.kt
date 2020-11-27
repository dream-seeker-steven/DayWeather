package cn.suyyy.dayweather.ui.city

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.suyyy.dayweather.R
import cn.suyyy.dayweather.ui.weather.WeatherActivity
import cn.suyyy.dayweather.logic.model.City

class CityAdapter(private val fragment: CityFragment, private val cityList: List<City>) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cityName: TextView = view.findViewById(R.id.cityName)
        val cityAddress: TextView = view.findViewById(R.id.cityAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val city = cityList[position]
            val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                val location = "${city.lon},${city.lat}"
                putExtra("city_location", location)
                putExtra("city_name", city.name)
            }
            // 存储 city
            fragment.viewModel.saveCity(city)
            fragment.startActivity(intent)
            fragment.activity?.finish()
        }
        return holder
    }

    override fun getItemCount() = cityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = cityList[position]
        holder.cityName.text = location.name
        holder.cityAddress.text = location.country + " " + location.province + " " + location.city
    }
}