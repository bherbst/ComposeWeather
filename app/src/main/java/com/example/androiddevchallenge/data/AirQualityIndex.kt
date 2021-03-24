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
package com.example.androiddevchallenge.data

inline class AirQualityIndex(
    val value: Int
) {
    fun label(): AirQualityLabel = when (value) {
        in 0..50 -> AirQualityLabel.Good
        in 51..100 -> AirQualityLabel.Moderate
        in 101..150 -> AirQualityLabel.UnhealthyForSome
        in 151..200 -> AirQualityLabel.Unhealthy
        in 201..300 -> AirQualityLabel.VeryUnhealthy
        else -> AirQualityLabel.Hazardous
    }
}

enum class AirQualityLabel(
    val displayText: String
) {
    Good("Good"),
    Moderate("Moderate"),
    UnhealthyForSome("Unhealthy for some"),
    Unhealthy("Unhealthy"),
    VeryUnhealthy("Very Unhealthy"),
    Hazardous("Hazardous")
}
