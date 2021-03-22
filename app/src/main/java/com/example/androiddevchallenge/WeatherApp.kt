package com.example.androiddevchallenge

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun WeatherApp() {
  Surface(color = MaterialTheme.colors.background) {
    Text(text = "Ready... Set... GO!")
  }
}