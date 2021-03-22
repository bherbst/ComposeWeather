package com.example.androiddevchallenge.weather

import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.data.staticTodayWeather

class WeatherViewModel : ViewModel() {

  // TODO This would usually come from an API
  val todaysWeather = staticTodayWeather


}