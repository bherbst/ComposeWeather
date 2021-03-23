package com.example.androiddevchallenge.data

data class Wind(
  val speed: MilesPerHour,
  val direction: WindDirection
)

enum class WindDirection(
  val displayText: String,
  val shortText: String,
) {
  North("North", "N"),
  Northeast("Northeast", "NE"),
  East("East", "E"),
  Southeast("Southeast", "SE"),
  South("South", "S"),
  Southwest("Southwest", "SW"),
  West("West", "W"),
  Northwest("Northwest", "NW")
}