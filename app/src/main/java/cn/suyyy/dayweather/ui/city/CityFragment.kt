package cn.suyyy.dayweather.ui.city

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.suyyy.dayweather.R
import cn.suyyy.dayweather.app.MainActivity
import cn.suyyy.dayweather.ui.weather.WeatherActivity
import cn.suyyy.dayweather.util.showToast
import kotlinx.android.synthetic.main.fragment_city.*


class CityFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this).get(CityViewModel::class.java)
    }

    private lateinit var adapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewModel.isCitySaved()) {
            val city = viewModel.getSaveCity()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                val location = "${city.lon},${city.lat}"
                putExtra("city_location", location)
                putExtra("city_name", city.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        if (activity is MainActivity) {
            viewModel.getHotCityList()
        }
        // 加载热门数据
        adapter = CityAdapter(this, viewModel.cityList)
        recyclerView.adapter = adapter

        searchCityEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchCityList(content)
            } else {
                viewModel.cityList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.cityLiveData.observe(viewLifecycleOwner, Observer { result ->
            val cityList = result.getOrNull()
            if (cityList != null) {
                viewModel.cityList.clear()
                recyclerView.visibility = View.VISIBLE
                viewModel.cityList.addAll(cityList)
                adapter.notifyDataSetChanged()
            } else {
                viewModel.cityList.clear()
                recyclerView.visibility = View.GONE
                adapter.notifyDataSetChanged()
                "未能查询到任何地点".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.hotCityLiveData.observe(viewLifecycleOwner, Observer { result ->
            val cityList = result.getOrNull()
            if (cityList != null) {
                recyclerView.visibility = View.VISIBLE
                viewModel.cityList.addAll(cityList)
                adapter.notifyDataSetChanged()
            }
        })
    }
}