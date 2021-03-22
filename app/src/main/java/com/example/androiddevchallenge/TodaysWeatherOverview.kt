package com.example.androiddevchallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.androiddevchallenge.data.HourOfDay
import com.example.androiddevchallenge.data.WeatherAtTime
import com.example.androiddevchallenge.data.WeatherForDay
import com.example.androiddevchallenge.data.staticTodayWeather

@Composable
fun TodaysWeatherOverview(
  weatherForDay: WeatherForDay
) {
  Column {
    LazyRow {
      items(weatherForDay.hourlyWeatherList) { (hour, weather) ->
        HourlyWeatherMini(hour, weather)
      }
    }
  }
}

/**
 * Mini display of hourly weather, used as a "tab" of sorts
 */
@Composable
fun HourlyWeatherMini(
  hour: HourOfDay,
  weather: WeatherAtTime
) {
  Column {
    Text(
      text = hour.displayString()
    )
    Text(
      text = "${weather.temp}°F"
    )
  }
}

@Preview
@Composable
private fun HourlyWeatherMiniPreview(
  @PreviewParameter(
    HourlyWeatherParameterProvider::class,
    limit = 3
  )
  weatherAtTime: Pair<HourOfDay, WeatherAtTime>
) {
  val (hour, weather) = weatherAtTime
  HourlyWeatherMini(hour, weather)
}

private class HourlyWeatherParameterProvider : PreviewParameterProvider<Pair<HourOfDay, WeatherAtTime>> {
  override val values = staticTodayWeather.hourlyWeather.entries
    .asSequence()
    .map { it.toPair() }
}