package com.example.androiddevchallenge.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.HourOfDay
import com.example.androiddevchallenge.data.WeatherAtTime
import com.example.androiddevchallenge.data.WeatherForDay
import com.example.androiddevchallenge.data.staticTodayWeather
import com.example.androiddevchallenge.settings.WeatherUnits
import com.example.androiddevchallenge.ui.theme.WeatherTheme

@Composable
fun HourlyWeatherStrip(
  dailyWeather: WeatherForDay,
  units: WeatherUnits,
  activeHour: HourOfDay,
) {
  LazyRow(
    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
  ) {
    items(dailyWeather.hourlyWeatherList) { (hour, weather) ->
      HourlyWeatherMini(
        modifier = Modifier.padding(horizontal = 16.dp),
        hour = hour,
        weather = weather,
        units = units,
        isActive = hour == activeHour
      )
    }
  }
}

/**
 * Mini display of hourly weather, used as a "tab" of sorts
 */
@Composable
private fun HourlyWeatherMini(
  modifier: Modifier = Modifier,
  hour: HourOfDay,
  weather: WeatherAtTime,
  units: WeatherUnits,
  isActive: Boolean,
) {
  val weight = if (isActive) FontWeight.Bold else FontWeight.Normal
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = hour.displayString(),
      style = MaterialTheme.typography.body1,
      fontWeight = weight,
    )
    Text(
      text = units.formatTemperature(weather.temp),
      style = MaterialTheme.typography.caption,
      fontWeight = weight,
    )
    // TODO icon
  }
}

@Preview
@Composable
private fun ActiveWeatherMiniPreview() {
  val (hour, weather) = staticTodayWeather.hourlyWeather.entries.first()
  WeatherTheme {
    HourlyWeatherMini(
      hour = hour,
      weather = weather,
      units = WeatherUnits.Imperial,
      isActive = true
    )
  }
}

@Preview
@Composable
private fun InactiveWeatherMiniPreview() {
  val (hour, weather) = staticTodayWeather.hourlyWeather.entries.first()
  WeatherTheme {
    HourlyWeatherMini(
      hour = hour,
      weather = weather,
      units = WeatherUnits.Imperial,
      isActive = false
    )
  }
}