/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
