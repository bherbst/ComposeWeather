package com.example.androiddevchallenge.data

import java.time.LocalDate

val staticTodayWeather = WeatherForDay(
  day = LocalDate.now(),
  highTemp = DegreesFahrenheit(58),
  lowTemp = DegreesFahrenheit(42),
  hourlyWeather = mapOf(
    HourOfDay(10) to WeatherAtTime(
      temp = DegreesFahrenheit(43),
      humidity = Percent(81),
      airQuality = AirQualityIndex(31),
      wind = Wind(
        speed = MilesPerHour(2),
        direction = WindDirection.Northeast
      ),
      precipitation = Precipitation.None
    ),

    HourOfDay(11) to WeatherAtTime(
      temp = DegreesFahrenheit(43),
      humidity = Percent(81),
      airQuality = AirQualityIndex(31),
      wind = Wind(
        speed = MilesPerHour(2),
        direction = WindDirection.Northeast
      ),
      precipitation = Precipitation.None
    ),

    HourOfDay(12) to WeatherAtTime(
      temp = DegreesFahrenheit(43),
      humidity = Percent(81),
      airQuality = AirQualityIndex(31),
      wind = Wind(
        speed = MilesPerHour(2),
        direction = WindDirection.Northeast
      ),
      precipitation = Precipitation.None
    ),

    HourOfDay(13) to WeatherAtTime(
      temp = DegreesFahrenheit(43),
      humidity = Percent(81),
      airQuality = AirQualityIndex(31),
      wind = Wind(
        speed = MilesPerHour(2),
        direction = WindDirection.Northeast
      ),
      precipitation = Precipitation.None
    ),

    HourOfDay(14) to WeatherAtTime(
      temp = DegreesFahrenheit(43),
      humidity = Percent(81),
      airQuality = AirQualityIndex(31),
      wind = Wind(
        speed = MilesPerHour(2),
        direction = WindDirection.Northeast
      ),
      precipitation = Precipitation.None
    ),

    HourOfDay(15) to WeatherAtTime(
      temp = DegreesFahrenheit(43),
      humidity = Percent(81),
      airQuality = AirQualityIndex(31),
      wind = Wind(
        speed = MilesPerHour(2),
        direction = WindDirection.Northeast
      ),
      precipitation = Precipitation.None
    ),

    HourOfDay(16) to WeatherAtTime(
      temp = DegreesFahrenheit(43),
      humidity = Percent(81),
      airQuality = AirQualityIndex(31),
      wind = Wind(
        speed = MilesPerHour(2),
        direction = WindDirection.Northeast
      ),
      precipitation = Precipitation.None
    )
  )
)