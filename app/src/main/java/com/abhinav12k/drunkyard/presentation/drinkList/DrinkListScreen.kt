package com.abhinav12k.drunkyard.presentation.drinkList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.R
import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.presentation.drinkList.components.DrinkCardsGrid
import com.abhinav12k.drunkyard.presentation.drinkList.components.HorizontalChipList
import com.abhinav12k.drunkyard.presentation.drinkList.components.SearchBar

@Composable
fun DrinkListScreen(
    viewModel: DrinkListViewModel
) {
    val viewState = viewModel.drinkListViewState.value

    Box(modifier = Modifier.fillMaxSize()) {
        DrinkListScreenContent(
            categories = viewModel.drinkCardCategories.value,
            drinkCards = viewModel.drinkCards.value,
            onSearchTextChanged = {
                viewModel.getDrinksBasedOnName(it)
            },
            onChipClicked = {
                viewModel.updateChipSelection(it)
                viewModel.getDrinkCardsByCategory(it.queryParam)
            },
            onDrinkCardClicked = {
                viewModel.getDrinkDetailsById(it)
            }
        )
        if (viewState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (!viewState.error.isNullOrBlank()) {
            Text(
                text = viewState.error,
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

@Composable
fun DrinkListScreenContent(
    modifier: Modifier = Modifier,
    categories: List<Category>?,
    drinkCards: List<DrinkCard>?,
    onSearchTextChanged: (drinkName: String) -> Unit,
    onChipClicked: (category: Category) -> Unit,
    onDrinkCardClicked: (drinkId: String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SearchBar(
            placeHolder = R.string.search_placeholder,
            modifier = modifier.padding(horizontal = 16.dp),
            onValueChange = {
                onSearchTextChanged(it)
            }
        )

        if (categories != null)
            HorizontalChipList(
                categories = categories,
                modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                onChipClicked = {
                    onChipClicked(it)
                }
            )
        if (drinkCards != null)
            DrinkCardsGrid(
                drinkCards = drinkCards,
                modifier = modifier.padding(horizontal = 8.dp),
                onClick = {
                    onDrinkCardClicked(it)
                }
            )
    }
}