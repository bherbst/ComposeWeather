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