package com.example.androiddevchallenge.data

import java.time.LocalDate

data class WeatherForDay(
  val day: LocalDate,
  val highTemp: DegreesFahrenheit,
  val lowTemp: DegreesFahrenheit,
  val hourlyWeather: Map<HourOfDay, WeatherAtTime>
) {
  val hourlyWeatherList by lazy {
    hourlyWeather.entries.asSequence()
      .sortedBy { it.key.value }
      .toList()
  }
}