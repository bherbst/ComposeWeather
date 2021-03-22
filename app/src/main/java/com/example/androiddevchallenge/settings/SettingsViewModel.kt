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