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
package com.example.androiddevchallenge.extensions

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo

/**
 * Get the index of the first fully visible item
 */
private val LazyListLayoutInfo.firstFullyVisibleItemIndex: Int
    get() = visibleItemsInfo.first { isFullyVisible(it) }.index

/**
 * Get the index of the last fully visible item
 */
private val LazyListLayoutInfo.lastFullyVisibleItemIndex: Int
    get() = visibleItemsInfo.last { isFullyVisible(it) }.index

private fun LazyListLayoutInfo.isFullyVisible(item: LazyListItemInfo): Boolean {
    return item.offset >= viewportStartOffset && (item.offset + item.size) <= viewportEndOffset
}

/**
 * Get an IntRange representing the indices of the currently fully visible items
 */
val LazyListLayoutInfo.fullyVisibleItemRange: IntRange
    get() = IntRange(
        start = firstFullyVisibleItemIndex,
        endInclusive = lastFullyVisibleItemIndex
    )
