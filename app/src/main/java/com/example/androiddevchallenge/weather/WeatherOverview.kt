package com.example.androiddevchallenge.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SweepGradientShader
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.data.DegreesFahrenheit
import com.example.androiddevchallenge.data.WeatherAtTime
import com.example.androiddevchallenge.data.WeatherForDay
import com.example.androiddevchallenge.data.staticTodayWeather
import com.example.androiddevchallenge.settings.SettingsViewModel
import com.example.androiddevchallenge.settings.WeatherUnits
import com.example.androiddevchallenge.ui.theme.WeatherTheme
import com.example.androiddevchallenge.ui.theme.orange
import com.example.androiddevchallenge.ui.theme.peach
import kotlinx.coroutines.launch
import java.time.LocalDate
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
  val pageSize = with(LocalDensity.current) { 128.dp.roundToPx() }
  val coroutineScope = rememberCoroutineScope()
  val hourState = remember {
    HourlyWeatherState(
      // In a real app we would find "now" and make that the active hour, but this will do for now
      activeHour = weatherForDay.hourlyWeather.keys.first(),
      minHour = weatherForDay.hourlyWeather.keys.first(),
      maxHour = weatherForDay.hourlyWeather.keys.last()
    )
  }

  val backgroundGradient = SweepGradientShader(
    center = Offset(0f, 0f),
    colors = listOf(
      peach,
      orange
    )
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(ShaderBrush(backgroundGradient))
      .draggable(
        orientation = Orientation.Horizontal,
        onDragStarted = {
          // TODO do we need this?
        },
        onDragStopped = { velocity ->
          coroutineScope.launch {
            hourState.fling(velocity / pageSize)
          }
        },
        state = rememberDraggableState { dy ->
          coroutineScope.launch {
            with(hourState) {
              val pos = pageSize * currentPageOffset
              val max = if (activeHour == minHour) 0 else pageSize
              val min = if (activeHour == maxHour) 0 else -pageSize
              val newPos = (pos + dy).coerceIn(min.toFloat(), max.toFloat())
              snapToOffset(newPos / pageSize)
            }
          }
        }
      )
  ) {
    TodayHeader(
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
      date = weatherForDay.day,
      highTemp = weatherForDay.highTemp,
      lowTemp = weatherForDay.lowTemp,
      units = units
    )

    Divider(
      modifier = Modifier.padding(
        vertical = 8.dp,
        horizontal = 8.dp
      )
    )

    Spacer(Modifier.height(8.dp))

    HourlyWeatherStrip(
      dailyWeather = weatherForDay,
      units = units,
      hourlyWeatherState = hourState
    )

    CurrentWeather(
      modifier = Modifier.fillMaxWidth(),
      weather = weatherForDay.hourlyWeather[hourState.activeHour]!!,
      units = units
    )
  }
}

@Composable
private fun TodayHeader(
  modifier: Modifier = Modifier,
  date: LocalDate,
  highTemp: DegreesFahrenheit,
  lowTemp: DegreesFahrenheit,
  units: WeatherUnits,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column {
      Text(
        text = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
        style = MaterialTheme.typography.h4
      )
      Spacer(Modifier.height(2.dp))
      Text(
        text = date.format(dateFormat),
        style = MaterialTheme.typography.h5
      )
    }

    Column {
      HighLowTemp(
        icon = Icons.Rounded.ArrowUpward,
        description = "High temperature",
        tempText = units.formatTemperature(highTemp)
      )
      Spacer(Modifier.height(8.dp))
      HighLowTemp(
        icon = Icons.Rounded.ArrowDownward,
        description = "Low temperature",
        tempText = units.formatTemperature(lowTemp)
      )
    }
  }
}

@Composable
private fun HighLowTemp(
  modifier: Modifier = Modifier,
  icon: ImageVector,
  description: String,
  tempText: String,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      modifier = Modifier.size(18.dp),
      imageVector = icon,
      contentDescription = description
    )
    Text(
      text = tempText,
      style = MaterialTheme.typography.body2
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