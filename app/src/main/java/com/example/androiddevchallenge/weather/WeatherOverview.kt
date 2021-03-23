package com.example.androiddevchallenge.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.data.WeatherAtTime
import com.example.androiddevchallenge.data.WeatherForDay
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

  WeatherOverview(
    weatherViewModel.todaysWeather,
    settingsViewModel.units
  )
}

@Composable
private fun WeatherOverview(
  weatherForDay: WeatherForDay,
  units: WeatherUnits
) {
  var activeHour by remember {
    mutableStateOf(
      // In a real app we would find "now", but this will do for now
      weatherForDay.hourlyWeather.keys.first()
    )
  }

  // TODO background gradient

  Column(
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
      Column {
        Text(
          text = weatherForDay.day.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
          style = MaterialTheme.typography.h4
        )
        Spacer(Modifier.height(2.dp))
        Text(
          text = weatherForDay.day.format(dateFormat),
          style = MaterialTheme.typography.h5
        )
      }

      // TODO high
      // TODO low
    }

    Divider(
      modifier = Modifier.padding(
        vertical = 8.dp,
        horizontal = 8.dp
      )
    )

    HourlyWeatherStrip(
      dailyWeather = weatherForDay,
      units = units,
      activeHour = activeHour
    )

    CurrentWeather(
      modifier = Modifier.fillMaxWidth(),
      weather = weatherForDay.hourlyWeather[activeHour]!!,
      units = units
    )
  }
}

@Composable
private fun CurrentWeather(
  modifier: Modifier = Modifier,
  weather: WeatherAtTime,
  units: WeatherUnits,
) {
  Box(
    modifier = modifier
  ) {
    // TODO Sun location

    Column(
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(Modifier.height(48.dp))
      WeatherTypeIcon(
        modifier = Modifier.size(164.dp),
        type = weather.weatherType,
        tint = Color.Black // TODO color
      )
      Spacer(Modifier.height(48.dp))

      HourlyWeatherDetails(
        modifier = Modifier.fillMaxWidth(.75f),
        weather = weather,
        units = units
      )
    }
  }
}

@Preview
@Composable
private fun WeatherOverviewPreview() {
  WeatherTheme {
    Surface {
      WeatherOverview(
        staticTodayWeather,
        WeatherUnits.Imperial
      )
    }
  }
}