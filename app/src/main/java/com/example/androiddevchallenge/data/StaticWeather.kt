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
      precipitation = Precipitation.None,
      weatherType = WeatherType.Sunny
    ),

    HourOfDay(11) to WeatherAtTime(
      temp = DegreesFahrenheit(56),
      humidity = Percent(65),
      airQuality = AirQualityIndex(28),
      wind = Wind(
        speed = MilesPerHour(4),
        direction = WindDirection.East
      ),
      precipitation = Precipitation.None,
      weatherType = WeatherType.Cloudy
    ),

    HourOfDay(12) to WeatherAtTime(
      temp = DegreesFahrenheit(62),
      humidity = Percent(52),
      airQuality = AirQualityIndex(26),
      wind = Wind(
        speed = MilesPerHour(8),
        direction = WindDirection.Southwest
      ),
      precipitation = Precipitation.Some(
        chance = Percent(30),
        type = PrecipitationType.Rain,
        amount = Inches(0.2)
      ),
      weatherType = WeatherType.Rainy
    ),

    HourOfDay(13) to WeatherAtTime(
      temp = DegreesFahrenheit(58),
      humidity = Percent(45),
      airQuality = AirQualityIndex(90),
      wind = Wind(
        speed = MilesPerHour(11),
        direction = WindDirection.Southwest
      ),
      precipitation = Precipitation.Some(
        chance = Percent(80),
        type = PrecipitationType.Mixed,
        amount = Inches(2.0)
      ),
      weatherType = WeatherType.RainySnowy
    ),

    HourOfDay(14) to WeatherAtTime(
      temp = DegreesFahrenheit(30),
      humidity = Percent(20),
      airQuality = AirQualityIndex(25),
      wind = Wind(
        speed = MilesPerHour(15),
        direction = WindDirection.West
      ),
      precipitation = Precipitation.Some(
        chance = Percent(100),
        type = PrecipitationType.Snow,
        amount = Inches(4.0)
      ),
      weatherType = WeatherType.Rainy
    ),

    HourOfDay(15) to WeatherAtTime(
      temp = DegreesFahrenheit(45),
      humidity = Percent(59),
      airQuality = AirQualityIndex(110),
      wind = Wind(
        speed = MilesPerHour(30),
        direction = WindDirection.West
      ),
      precipitation = Precipitation.Some(
        chance = Percent(100),
        type = PrecipitationType.Rain,
        amount = Inches(3.0)
      ),
      weatherType = WeatherType.Tornado
    ),

    HourOfDay(16) to WeatherAtTime(
      temp = DegreesFahrenheit(43),
      humidity = Percent(81),
      airQuality = AirQualityIndex(80),
      wind = Wind(
        speed = MilesPerHour(3),
        direction = WindDirection.North
      ),
      precipitation = Precipitation.None,
      weatherType = WeatherType.Sunny
    )
  )
)