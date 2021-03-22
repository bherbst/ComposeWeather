package com.example.androiddevchallenge.data

data class Wind(
  val speed: MilesPerHour,
  val direction: WindDirection
)

enum class WindDirection(
  val displayText: String,
) {
  North("North"),
  Northeast("Northeast"),
  East("East"),
  Southeast("Southeast"),
  South("South"),
  Southwest("Southwest"),
  West("West"),
  Northwest("Northwest")
}