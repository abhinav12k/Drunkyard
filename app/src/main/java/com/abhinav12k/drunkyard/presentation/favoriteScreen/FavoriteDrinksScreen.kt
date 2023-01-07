package com.abhinav12k.drunkyard.presentation.favoriteScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.common.header
import com.abhinav12k.drunkyard.presentation.drinkList.components.DrinkCard

@Composable
fun FavoriteDrinksScreen(
    viewModel: FavoriteDrinksViewModel,
    onDrinkCardClicked: (drinkId: String) -> Unit
) {
    val favoriteDrinks = viewModel.favoriteDrinks

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (!favoriteDrinks.value.isNullOrEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    header {
                        Text(
                            text = "Favorites",
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                    favoriteDrinks.value?.let {
                        items(it) { drink ->
                            DrinkCard(drinkCard = drink, onClick = onDrinkCardClicked)
                        }
                    }
                }
            } else {
                Text(
                    text = "You don't have any favorites add one now!",
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}