package com.example.androiddevchallenge.data

import kotlin.math.roundToInt

fun Inches.toCm() = Centimeters(value * 2.54)
fun MilesPerHour.toKilometers() = KilometersPerHour((value * 1.609).roundToInt())
fun DegreesFahrenheit.toCelsius() = DegreesCelsius(((value - 32) / 1.8f).roundToInt())