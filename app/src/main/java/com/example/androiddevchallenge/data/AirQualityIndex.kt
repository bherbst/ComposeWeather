package com.example.androiddevchallenge.data

inline class AirQualityIndex(val value: Int)

enum class AirQualityLabel {
  Good,
  Moderate,
  UnhealthyForSome,
  Unhealthy,
  VeryUnhealthy,
  Hazardous
}