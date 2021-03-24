/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.weather

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SweepGradientShader
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.data.DegreesFahrenheit
import com.example.androiddevchallenge.data.HourOfDay
import com.example.androiddevchallenge.data.WeatherAtTime
import com.example.androiddevchallenge.data.WeatherForDay
import com.example.androiddevchallenge.data.staticTodayWeather
import com.example.androiddevchallenge.settings.SettingsViewModel
import com.example.androiddevchallenge.settings.WeatherUnits
import com.example.androiddevchallenge.ui.Pager
import com.example.androiddevchallenge.ui.PagerState
import com.example.androiddevchallenge.ui.theme.WeatherTheme
import com.example.androiddevchallenge.ui.theme.yellowBold
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
    val pagerState = remember {
        PagerState(maxPage = weatherForDay.hourlyWeather.size - 1)
    }
    val activeHour by derivedStateOf {
        weatherForDay.hourlyWeatherList[pagerState.currentPage].key
    }

    val gradientStart by animateColorAsState(
        targetValue = WeatherGradientCalculator.gradientStartForHour(activeHour),
        animationSpec = spring()
    )
    val gradientEnd by animateColorAsState(
        targetValue = WeatherGradientCalculator.gradientEndForHour(activeHour),
        animationSpec = spring()
    )

    val backgroundGradient = SweepGradientShader(
        center = Offset(0f, 0f),
        colors = listOf(
            gradientStart,
            gradientEnd
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ShaderBrush(backgroundGradient))
            .drawSunAtPosition(
                hour = activeHour,
                hourOffset = pagerState.currentPageOffset
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

        HourlyWeatherStrip(
            dailyWeather = weatherForDay,
            units = units,
            activeHour = activeHour,
            onHourClick = { hour ->
                pagerState.currentPage = weatherForDay.hourlyWeatherList.indexOfFirst { it.key == hour }
            }
        )

        Pager(
            state = pagerState
        ) {
            val weather = weatherForDay.hourlyWeatherList[page].value
            CurrentWeather(
                modifier = Modifier.fillMaxWidth(),
                weather = weather,
                units = units
            )
        }
    }
}

/**
 * Draw a sun along an arc in the background of this view
 */
fun Modifier.drawSunAtPosition(
    hour: HourOfDay,
    hourOffset: Float
) = drawBehind {
    val sunPathRadius = .4f * size.width
    // The sun starts at π and ends at 0, 6am to 6pm
    // We don't have to handle times outside that because they don't exist in our
    // insulated world
    val theta = PI - (PI * ((hour.value - hourOffset - 6) / 12f))
    val sunX = center.x + sunPathRadius * cos(theta)
    val sunY = center.y - sunPathRadius * sin(theta)

    drawCircle(
        color = yellowBold,
        alpha = .3f,
        radius = 32.dp.roundToPx().toFloat(),
        center = Offset(sunX.toFloat(), sunY.toFloat())
    )
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
        modifier = modifier.semantics(mergeDescendants = true) {},
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
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))
            Row(
                modifier = Modifier.semantics(mergeDescendants = true) {},
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherTypeIcon(
                    modifier = Modifier.size(164.dp),
                    type = weather.weatherType
                )
                Row {
                    Text(
                        text = units.convertTemperature(weather.temp).toString(),
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp), // Hack until includeFontPadding exists
                        text = units.degreesLabel,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
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
