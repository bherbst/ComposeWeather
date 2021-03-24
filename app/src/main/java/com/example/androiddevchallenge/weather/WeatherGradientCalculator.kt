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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import com.example.androiddevchallenge.data.HourOfDay
import com.example.androiddevchallenge.ui.theme.coral
import com.example.androiddevchallenge.ui.theme.orange
import com.example.androiddevchallenge.ui.theme.peach

object WeatherGradientCalculator {
    private val gradientStart10am = peach
    private val gradientEnd10am = coral

    private val gradientStart2pm = peach
    private val gradientEnd2pm = orange

    private val gradientStart6pm = coral
    private val gradientEnd6pm = peach

    fun gradientStartForHour(hour: HourOfDay): Color {
        return when (hour.value) {
            in 10..14 -> {
                val fraction = .25f * (hour.value - 10)
                lerp(gradientStart10am, gradientStart2pm, fraction)
            }
            in 14..18 -> {
                val fraction = .25f * (hour.value - 14)
                lerp(gradientStart2pm, gradientStart6pm, fraction)
            }
            else -> {
                // We won't ever actually fall in here because our static
                // data only goes from 10am to 6pm
                throw IllegalArgumentException("That hour doesn't exist!")
            }
        }
    }

    fun gradientEndForHour(hour: HourOfDay): Color {
        return when (hour.value) {
            in 10..14 -> {
                val fraction = .25f * (hour.value - 10)
                lerp(gradientEnd10am, gradientEnd2pm, fraction)
            }
            in 14..18 -> {
                val fraction = .25f * (hour.value - 14)
                lerp(gradientEnd2pm, gradientEnd6pm, fraction)
            }
            else -> {
                // We won't ever actually fall in here because our static
                // data only goes from 10am to 6pm
                throw IllegalArgumentException("That hour doesn't exist!")
            }
        }
    }
}
