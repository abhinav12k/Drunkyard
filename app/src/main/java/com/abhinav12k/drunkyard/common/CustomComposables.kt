package com.abhinav12k.drunkyard.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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