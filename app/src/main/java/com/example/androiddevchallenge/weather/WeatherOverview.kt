package com.example.androiddevchallenge.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.HourOfDay
import com.example.androiddevchallenge.data.WeatherAtTime
import com.example.androiddevchallenge.data.WeatherForDay
import com.example.androiddevchallenge.data.WeatherType
import com.example.androiddevchallenge.data.staticTodayWeather
import com.example.androiddevchallenge.settings.SettingsViewModel
import com.example.androiddevchallenge.settings.WeatherUnits
import com.example.androiddevchallenge.ui.theme.WeatherTheme
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val dateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy")

@Composable
fun WeatherOverview() {
  val weatherViewModel: WeatherViewModel = viewModel()
  val settingsViewModel: SettingsViewModel = viewModel()
  val date = weatherViewModel.todaysWeather.day
  var activeHour by remember {
    mutableStateOf(
      // In a real app we would find "now", but this will do for now
      weatherViewModel.todaysWeather.hourlyWeather.keys.first()
    )
  }

  // TODO background gradient

  Column(
    modifier = Modifier.padding(16.dp)
  ) {
    Text(
      text = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
      style = MaterialTheme.typography.h4
    )
    Spacer(Modifier.height(2.dp))
    Text(
      text = date.format(dateFormat),
      style = MaterialTheme.typography.h5
    )

    // TODO high
    // TODO low

    Divider(
      modifier = Modifier.padding(
        vertical = 8.dp,
        horizontal = 8.dp
      )
    )

    HourlyWeatherStrip(
      dailyWeather = weatherViewModel.todaysWeather,
      units = settingsViewModel.units,
      activeHour = activeHour
    )

    CurrentWeather(
      weather = weatherViewModel.todaysWeather.hourlyWeather[activeHour]!!,
      units = settingsViewModel.units
    )
  }
}

@Composable
private fun HourlyWeatherStrip(
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

@Composable
fun WeatherAtTime.weatherTypeIcon(): Painter = when (weatherType) {
  WeatherType.Sunny -> painterResource(R.drawable.ic_sunny)
  WeatherType.Cloudy -> painterResource(R.drawable.ic_cloudy)
  WeatherType.Rainy -> painterResource(R.drawable.ic_rain)
  WeatherType.Snowy -> painterResource(R.drawable.ic_snow)
  WeatherType.RainySnowy -> painterResource(R.drawable.ic_snow_rain)
  WeatherType.Tornado -> painterResource(R.drawable.ic_tornado)
}

fun WeatherAtTime.weatherTypeDescription(): String = when (weatherType) {
  WeatherType.Sunny -> "Sunny"
  WeatherType.Cloudy -> "Cloudy"
  WeatherType.Rainy -> "Rainy"
  WeatherType.Snowy -> "Snowy"
  WeatherType.RainySnowy -> "Mixed rain and snow"
  WeatherType.Tornado -> "Tornado"
}

@Composable
private fun CurrentWeather(
  modifier: Modifier = Modifier,
  weather: WeatherAtTime,
  units: WeatherUnits,
) {
  Box {
    // TODO Sun location

    Column(
      modifier = Modifier.padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        modifier = Modifier.size(164.dp),
        painter = weather.weatherTypeIcon(),
        contentDescription = weather.weatherTypeDescription(),
        tint = Color.Black // TODO color
      )

      Row {
        Column {
          // TODO precipitation
          // TODO Icon of rain/snow/mix

        }
        Divider()
        Column {
          // TODO wind
          // TODO Icon of rain/snow/mix

        }
      }

      // TODO humidity
      // TODO AQI
    }
  }
}



@Preview(backgroundColor = 0xFFFFFFFF)
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

@Preview(backgroundColor = 0xFFFFFFFF)
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

private class HourlyWeatherParameterProvider : PreviewParameterProvider<Pair<HourOfDay, WeatherAtTime>> {
  override val values = staticTodayWeather.hourlyWeather.entries
    .asSequence()
    .map { it.toPair() }
}