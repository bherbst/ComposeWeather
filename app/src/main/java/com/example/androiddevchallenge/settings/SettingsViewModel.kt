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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    var units: WeatherUnits by mutableStateOf(WeatherUnits.Imperial)
    var location: Location by mutableStateOf(Location.Minneapolis)

    /**
     * This is a silly implementation every time the text changes we just switch the location
     */
    fun updateLocation(locationText: String) {
        location = when (location) {
            Location.MountainView -> Location.Minneapolis
            Location.Minneapolis -> Location.MountainView
        }
    }
}
