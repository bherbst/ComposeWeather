package com.example.androiddevchallenge.weather

import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.WeatherType

@Composable
fun WeatherTypeIcon(
  modifier: Modifier = Modifier,
  type: WeatherType,
  tint: Color = LocalContentColor.current
) {
  when (type) {
    WeatherType.Sunny -> Icon(
      modifier = modifier,
      painter = painterResource(R.drawable.ic_sunny),
      contentDescription = "Sunny",
      tint = tint
    )
    WeatherType.Cloudy -> Icon(
      modifier = modifier,
      painter = painterResource(R.drawable.ic_cloudy),
      contentDescription = "Cloudy",
      tint = tint
    )
    WeatherType.Rainy -> Icon(
      modifier = modifier,
      painter = painterResource(R.drawable.ic_rain),
      contentDescription = "Rainy",
      tint = tint
    )
    WeatherType.Snowy -> Icon(
      modifier = modifier,
      painter = painterResource(R.drawable.ic_snow),
      contentDescription = "Snowy",
      tint = tint
    )
    WeatherType.RainySnowy -> Icon(
      modifier = modifier,
      painter = painterResource(R.drawable.ic_snow_rain),
      contentDescription = "Snowy and rainy",
      tint = tint
    )
    WeatherType.Tornado -> Icon(
      modifier = modifier,
      painter = painterResource(R.drawable.ic_tornado),
      contentDescription = "Tornado",
      tint = tint
    )
  }
}