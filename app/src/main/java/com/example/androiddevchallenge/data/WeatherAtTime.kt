package com.example.androiddevchallenge.data

data class WeatherAtTime(
  val temp: DegreesFahrenheit,
  val humidity: Percent,
  val airQuality: AirQualityIndex,
  val precipitation: Precipitation,
  val wind: Wind,
  val weatherType: WeatherType,
)