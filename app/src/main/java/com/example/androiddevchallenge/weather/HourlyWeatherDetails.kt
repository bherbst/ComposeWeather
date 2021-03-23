package com.example.androiddevchallenge.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.AirQualityIndex
import com.example.androiddevchallenge.data.DegreesFahrenheit
import com.example.androiddevchallenge.data.Inches
import com.example.androiddevchallenge.data.MilesPerHour
import com.example.androiddevchallenge.data.Percent
import com.example.androiddevchallenge.data.Precipitation
import com.example.androiddevchallenge.data.PrecipitationType
import com.example.androiddevchallenge.data.WeatherAtTime
import com.example.androiddevchallenge.data.WeatherType
import com.example.androiddevchallenge.data.Wind
import com.example.androiddevchallenge.data.WindDirection
import com.example.androiddevchallenge.settings.WeatherUnits
import com.example.androiddevchallenge.ui.VerticalDivider
import com.example.androiddevchallenge.ui.theme.WeatherTheme

@Composable
fun HourlyWeatherDetails(
  modifier: Modifier = Modifier,
  weather: WeatherAtTime,
  units: WeatherUnits
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Precipitation(
        modifier = Modifier.fillMaxWidth(.5f)
          .padding(16.dp),
        precipitation = weather.precipitation,
        units = units
      )
      VerticalDivider(
        modifier = Modifier.height(64.dp)
      )
      Wind(
        modifier = Modifier.fillMaxWidth()
          .padding(16.dp),
        wind = weather.wind,
        units = units
      )
    }

    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ){
      Humidity(
        modifier = Modifier.fillMaxWidth(.5f)
          .padding(16.dp),
        humidity = weather.humidity
      )
      VerticalDivider(
        modifier = Modifier.height(64.dp)
      )
      AirQualityIndex(
        modifier = Modifier.fillMaxWidth()
          .padding(16.dp),
        aqi = weather.airQuality
      )
    }
  }
}

@Composable
private fun Precipitation(
  modifier: Modifier = Modifier,
  precipitation: Precipitation,
  units: WeatherUnits
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row {
      PrecipitationIcon(precipitation.type)
      Spacer(Modifier.width(4.dp))
      Text(
        text = "${precipitation.chance.value}%"
      )
    }
    Spacer(Modifier.height(4.dp))
    Text(units.formatAccumulation(precipitation.amount))
  }
}

@Composable
private fun PrecipitationIcon(type: PrecipitationType) {
  when (type) {
    PrecipitationType.Snow -> Icon(
      painter = painterResource(R.drawable.ic_snowflake),
      contentDescription = "snow"
    )
    PrecipitationType.Mixed -> Icon(
      painter = painterResource(R.drawable.ic_snowflake_raindrop),
      contentDescription = "rain and snow"
    )
    else -> Icon(
      painter = painterResource(R.drawable.ic_raindrop),
      contentDescription = "rain"
    )
  }
}

@Composable
private fun Wind(
  modifier: Modifier = Modifier,
  wind: Wind,
  units: WeatherUnits
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row {
      Icon(
        painter = painterResource(R.drawable.ic_wind),
        contentDescription = "Wind"
      )
      Spacer(Modifier.width(4.dp))
      Text(units.formatSpeed(wind.speed))
    }
    Spacer(Modifier.height(4.dp))
    Row {
      Icon(
        modifier = Modifier.rotate(wind.iconRotationDegrees()),
        imageVector = Icons.Rounded.Navigation,
        contentDescription = null // The text label will suffice
      )
      Spacer(Modifier.width(4.dp))
      Text(wind.direction.shortText)
    }
  }
}

/**
 * Get the amount in degrees to rotate the wind direction icon,
 * which is pointing directly upward at zero rotation.
 */
private fun Wind.iconRotationDegrees(): Float = when (direction) {
  WindDirection.North -> 0f
  WindDirection.Northeast -> 45f
  WindDirection.East -> 90f
  WindDirection.Southeast -> 135f
  WindDirection.South -> 180f
  WindDirection.Southwest -> 225f
  WindDirection.West -> 270f
  WindDirection.Northwest -> 315f
}

@Composable
private fun AirQualityIndex(
  modifier: Modifier = Modifier,
  aqi: AirQualityIndex
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // TODO color
    Text(text = "${aqi.value} AQI")
    Spacer(Modifier.height(4.dp))
    Text(text = aqi.label().displayText)
  }
}

@Composable
private fun Humidity(
  modifier: Modifier = Modifier,
  humidity: Percent
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text("${humidity.value}%")
    Spacer(Modifier.height(4.dp))
    Text("Humidity")
  }
}

@Preview
@Composable
private fun HourlyWeatherDetailsPreview() {
  val weather = WeatherAtTime(
    temp = DegreesFahrenheit(43),
    humidity = Percent(81),
    airQuality = AirQualityIndex(31),
    wind = Wind(
      speed = MilesPerHour(2),
      direction = WindDirection.Northeast
    ),
    precipitation = Precipitation(
      chance = Percent(20),
      type = PrecipitationType.Rain,
      amount = Inches(0.2)
    ),
    weatherType = WeatherType.Sunny
  )
  WeatherTheme {
    Surface {
      HourlyWeatherDetails(
        modifier = Modifier.fillMaxWidth(),
        weather = weather,
        units = WeatherUnits.Imperial
      )
    }
  }
}