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