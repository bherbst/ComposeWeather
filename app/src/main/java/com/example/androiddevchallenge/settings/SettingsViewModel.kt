package com.example.androiddevchallenge.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
  var units: WeatherUnits by mutableStateOf(WeatherUnits.Imperial)
  var location: Location by mutableStateOf(Location.Minneapolis)

  /**
   * This is a silly implementation- enter "Mountain View, CA" for Mountain View, California. Everything
   * else is interpreted as Minneapolis, MN
   */
  fun updateLocation(locationText: String) {
    location = when (locationText) {
      Location.MountainView.displayText -> Location.MountainView
      else -> Location.Minneapolis
    }
  }
}