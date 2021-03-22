package com.example.androiddevchallenge.data

import androidx.annotation.IntRange

inline class HourOfDay(
  @IntRange(from = 0, to = 23)
  val value: Int
) {
  fun displayString(): String {
    val periodText = if (value >= 12) "pm" else "am"
    val hour = when {
      value == 0 -> 12
      value > 12 -> value - 12
      else -> value
    }
    return "$hour$periodText"
  }
}