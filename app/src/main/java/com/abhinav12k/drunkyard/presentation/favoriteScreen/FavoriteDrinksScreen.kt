package com.abhinav12k.drunkyard.presentation.favoriteScreen

import androidx.compose.foundation.layout.*
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
import com.abhinav12k.drunkyard.presentation.drinkList.DrinkListViewModel
import com.abhinav12k.drunkyard.presentation.drinkList.components.DrinkCard

@Composable
fun FavoriteDrinksScreen(
    viewModel: DrinkListViewModel,
    onDrinkCardClicked: (drinkId: String) -> Unit
) {
    val favoriteDrinks = viewModel.allFavoriteDrinks

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (favoriteDrinks.value.isNotEmpty()) {
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
                    items(favoriteDrinks.value) { drink ->
                        DrinkCard(drinkCard = drink, onClick = onDrinkCardClicked)
                    }
                }
            } else {
                Text(
                    text = "You don't have any favorites add one now!",
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}