package com.fifed.githubrepositories.extension

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.findLastVisibleItem() = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1