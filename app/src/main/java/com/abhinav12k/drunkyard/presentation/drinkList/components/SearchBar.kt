package com.abhinav12k.drunkyard.presentation.drinkList.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.R
import com.abhinav12k.drunkyard.common.clearFocusOnKeyboardDismiss
import com.abhinav12k.drunkyard.presentation.ui.theme.DrunkyardTheme
import com.abhinav12k.drunkyard.presentation.ui.theme.SearchBarDarkBackground

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    @StringRes placeHolder: Int,
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    TextField(
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            backgroundColor = if (isSystemInDarkTheme()) SearchBarDarkBackground else MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                stringResource(id = placeHolder),
                color = MaterialTheme.colors.onSurface.copy(alpha = .5f)
            )
        },
        shape = CircleShape,
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
        trailingIcon = {
            when { value.isNotEmpty() ->
                Icon(Icons.Default.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier
                        .clickable {
                            onValueChange("")
                        }
                )
            }
        }
    )
    if(value.isEmpty()) {
        val focusManager = LocalFocusManager.current
        focusManager.clearFocus()
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