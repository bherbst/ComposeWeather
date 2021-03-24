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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.HourOfDay
import com.example.androiddevchallenge.data.WeatherAtTime
import com.example.androiddevchallenge.data.WeatherForDay
import com.example.androiddevchallenge.data.staticTodayWeather
import com.example.androiddevchallenge.extensions.fullyVisibleItemRange
import com.example.androiddevchallenge.settings.WeatherUnits

/**
 * Tab strip that shows a mini hourly weather preview
 */
@Composable
fun HourlyWeatherStrip(
    dailyWeather: WeatherForDay,
    units: WeatherUnits,
    activeHour: HourOfDay,
    onHourClick: (hour: HourOfDay) -> Unit,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(activeHour) {
        val index = dailyWeather.hourlyWeatherList.indexOfFirst { it.key == activeHour }
        if (index !in listState.layoutInfo.fullyVisibleItemRange) {
            listState.animateScrollToItem(index)
        }
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
    ) {
        items(dailyWeather.hourlyWeatherList) { (hour, weather) ->
            HourlyWeatherMini(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                hour = hour,
                weather = weather,
                units = units,
                isActive = hour == activeHour,
                onClick = { onHourClick(hour) }
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
    onClick: () -> Unit,
) {
    val weight = if (isActive) FontWeight.Bold else FontWeight.Normal
    Column(
        modifier = Modifier.clickable(
            role = Role.Tab,
            onClick = onClick
        ).then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hour.displayString(),
            style = MaterialTheme.typography.body1,
            fontWeight = weight,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherTypeIcon(
                type = weather.weatherType
            )
            Spacer(Modifier.width(2.dp))
            Text(
                text = units.formatTemperature(weather.temp),
                style = MaterialTheme.typography.caption,
                fontWeight = weight,
            )
        }
    }
}

@Preview
@Composable
private fun HourTabsPreview() {
    HourlyWeatherStrip(
        dailyWeather = staticTodayWeather,
        units = WeatherUnits.Imperial,
        activeHour = HourOfDay(11),
        onHourClick = {}
    )
}
