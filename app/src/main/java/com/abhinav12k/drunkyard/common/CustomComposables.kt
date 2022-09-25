package com.abhinav12k.drunkyard.common

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager

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