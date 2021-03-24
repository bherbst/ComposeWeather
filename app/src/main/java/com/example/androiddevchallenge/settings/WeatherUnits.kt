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
package com.example.androiddevchallenge.settings

import com.example.androiddevchallenge.data.DegreesFahrenheit
import com.example.androiddevchallenge.data.Inches
import com.example.androiddevchallenge.data.MilesPerHour
import com.example.androiddevchallenge.data.toCelsius
import com.example.androiddevchallenge.data.toCm
import com.example.androiddevchallenge.data.toKilometers

/**
 * Units for Weather-y things
 *
 * @param unitSamples Sample units to display to the user
 * @param degreesLabel Label used for temperatures
 * @param accumulationLabel Label used for rain/snow accumulation
 * @param speedLabel Label used for speed (e.g. wind speed)
 */
enum class WeatherUnits(
    val unitSamples: String,
    val degreesLabel: String,
    val accumulationLabel: String,
    val speedLabel: String,
) {
    Imperial(
        unitSamples = "inches, °F, MPH",
        degreesLabel = "°F",
        accumulationLabel = "in",
        speedLabel = "MPH",
    ),
    Metric(
        unitSamples = "cm, °C, km/h",
        degreesLabel = "°C",
        accumulationLabel = "cm",
        speedLabel = "km/h",
    );

    fun convertTemperature(temp: DegreesFahrenheit) = when (this) {
        Imperial -> temp.value
        Metric -> temp.toCelsius().value
    }

    fun formatTemperature(temp: DegreesFahrenheit): String {
        val converted = convertTemperature(temp)
        return "$converted$degreesLabel"
    }

    fun formatAccumulation(amount: Inches): String {
        val converted = when (this) {
            Imperial -> amount.value
            Metric -> amount.toCm().value
        }
        return "$converted$accumulationLabel"
    }

    fun formatSpeed(speed: MilesPerHour): String {
        val converted = when (this) {
            Imperial -> speed.value
            Metric -> speed.toKilometers().value
        }
        return "$converted$speedLabel"
    }
}
