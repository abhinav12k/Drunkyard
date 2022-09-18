package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.R
import com.abhinav12k.drunkyard.presentation.ui.theme.DrunkyardTheme

@Composable
fun SearchBar(
    @StringRes placeHolder: Int,
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    Surface(
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = modifier
    ) {
        TextField(
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(stringResource(id = placeHolder))
            },
            shape = CircleShape,
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    DrunkyardTheme {
        SearchBar(
            placeHolder = R.string.search_placeholder,
            modifier = Modifier.padding(8.dp),
            value = ""
        ) {}
    }
}