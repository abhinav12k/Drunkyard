package com.abhinav12k.drunkyard.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import java.lang.Integer.min

@Composable
fun <T> CustomVertical2DRow(
    modifier: Modifier = Modifier,
    items: List<T>,
    content: @Composable RowScope.(modifier: Modifier, index: Int) -> Unit
) {
    var index = 0
    while (index < items.size - 1) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()
        ) {
            content(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                index
            )
            content(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                index + 1
            )
        }
        index += 2
    }
}

@Composable
fun <T> CustomVerticalRowWithColumns(
    modifier: Modifier = Modifier,
    items: List<T>,
    numOfColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround,
    resetArrangementIfOneItemInRow: Boolean = false,
    content: @Composable RowScope.(modifier: Modifier, index: Int) -> Unit
) {
    var index = 0
    var updatedArrangement = if(resetArrangementIfOneItemInRow && items.size == 1) Arrangement.Start else horizontalArrangement
    while (index < items.size) {
        Row(
            horizontalArrangement = updatedArrangement,
            modifier = modifier.fillMaxWidth()
        ) {
            for (i in index until min(index + numOfColumns, items.size)) {
                content(
                    Modifier,
                    i
                )
            }
        }
        index += min(numOfColumns, items.size)
        if(resetArrangementIfOneItemInRow && items.size % index == 1) updatedArrangement = Arrangement.Start
    }
}

@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
    var isFocused by remember { mutableStateOf(false) }
    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
    if (isFocused) {
        val imeIsVisible = WindowInsets.isImeVisible
        val focusManager = LocalFocusManager.current
        LaunchedEffect(imeIsVisible) {
            if (imeIsVisible) {
                keyboardAppearedSinceLastFocused = true
            } else if (keyboardAppearedSinceLastFocused) {
                focusManager.clearFocus()
            }
        }
    }
    onFocusEvent {
        if (isFocused != it.isFocused) {
            isFocused = it.isFocused
            if (isFocused) {
                keyboardAppearedSinceLastFocused = false
            }
        }
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}