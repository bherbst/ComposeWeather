package com.example.androiddevchallenge.settings

import com.example.androiddevchallenge.data.DegreesFahrenheit
import com.example.androiddevchallenge.data.toCelsius

/**
 * Units for Weather-y things
 *
 * @param unitSamples Sample units to display to the user
 * @param degreesLabel Label used for temperatures
 */
enum class WeatherUnits(
  val unitSamples: String,
  val degreesLabel: String
) {
  Imperial(
    unitSamples = "inches, °F, MPH",
    degreesLabel = "°F"
  ),
  Metric(
    unitSamples = "cm, °C, km/h",
    degreesLabel = "°C"
  );

  fun formatTemperature(temp: DegreesFahrenheit): String {
    return when (this) {
      Imperial -> "${temp.value}${degreesLabel}"
      Metric -> {
        val converted = temp.toCelsius()
        "$converted${degreesLabel}"
      }
    }
  }
}