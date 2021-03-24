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
package com.example.androiddevchallenge.data

import androidx.annotation.IntRange

inline class HourOfDay(
    @IntRange(from = 0, to = 23)
    val value: Int
) {
    fun displayString(): String {
        val periodText = if (value >= 12) "pm" else "am"
        val hour = when {
            value == 0 -> 12
            value > 12 -> value - 12
            else -> value
        }
        return "$hour$periodText"
    }

    operator fun minus(hours: Int): HourOfDay {
        val result = this.value - hours

        // TODO extract clamping
        return when {
            result < 0 -> HourOfDay(result + 24)
            result > 23 -> HourOfDay(result - 24)
            else -> HourOfDay(result)
        }
    }
}
