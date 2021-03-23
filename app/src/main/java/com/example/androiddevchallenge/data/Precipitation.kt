package com.example.androiddevchallenge.data

data class Precipitation(
  val chance: Percent,
  val type: PrecipitationType,
  val amount: Inches,
)

enum class PrecipitationType {
  Snow,
  Rain,
  Mixed
}