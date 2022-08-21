package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.R
import com.abhinav12k.drunkyard.presentation.ui.theme.DrunkyardTheme

@Composable
fun SearchBar(
    @StringRes placeHolder: Int,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    TextField(
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        placeholder = {
            Text(stringResource(id = placeHolder))
        },
        value = "",
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(30.dp)
    )
}

@Preview
@Composable
fun PreviewSearchBar() {
    DrunkyardTheme {
        SearchBar(placeHolder = R.string.search_placeholder, modifier = Modifier.padding(8.dp)) {}
    }
}