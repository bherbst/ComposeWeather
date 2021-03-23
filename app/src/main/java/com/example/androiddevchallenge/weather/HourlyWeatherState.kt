package com.example.androiddevchallenge.weather

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.androiddevchallenge.data.HourOfDay
import kotlin.math.roundToInt

class HourlyWeatherState(
  activeHour: HourOfDay,
  val minHour: HourOfDay,
  val maxHour: HourOfDay
) {
  var activeHour by mutableStateOf(activeHour)

  val activeHourIndex: Int
    get() = activeHour.value - minHour.value

  private var _currentPageOffset = Animatable(0f).apply {
    updateBounds(-1f, 1f)
  }
  val currentPageOffset: Float
    get() = _currentPageOffset.value

  suspend fun snapToOffset(offset: Float) {
    val max = if (activeHour == minHour) 0f else 1f
    val min = if (activeHour == maxHour) 0f else -1f
    _currentPageOffset.snapTo(offset.coerceIn(min, max))
  }

  suspend fun fling(velocity: Float) {
    if (velocity < 0 && activeHour == minHour) return
    if (velocity > 0 && activeHour == maxHour) return

    _currentPageOffset.animateTo(currentPageOffset.roundToInt().toFloat())
    selectPage()
  }

  suspend fun selectPage() {
    activeHour -= currentPageOffset.roundToInt()
    snapToOffset(0f)
//    selectionState = PagerState.SelectionState.Selected
  }

  override fun toString() = "HourlyWeatherState{activeHour=$activeHour, minHour=$minHour, maxHour=$maxHour " +
    "activeHourIndex+$activeHourIndex, currentPageOffset=$currentPageOffset}"
}