package com.example.androiddevchallenge.data

sealed class Precipitation {
  object None: Precipitation()
  data class Some(
    val chance: Percent,
    val type: PrecipitationType,
    val amount: Inches,
  ): Precipitation()
}

enum class PrecipitationType {
  None,
  Snow,
  Rain,
  Mixed
}