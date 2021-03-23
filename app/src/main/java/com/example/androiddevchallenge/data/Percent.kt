package com.example.androiddevchallenge.data

// We currently only support whole-int percentages for weather data
inline class Percent(val value: Int) {
  fun toFloat() = value / 100f
}