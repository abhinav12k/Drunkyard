package com.abhinav12k.drunkyard.presentation.drinkList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abhinav12k.drunkyard.R
import com.abhinav12k.drunkyard.common.BackPressHandler
import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.presentation.drinkList.components.*
import com.abhinav12k.drunkyard.presentation.drinkList.model.DrinkSection
import com.abhinav12k.drunkyard.presentation.favoriteScreen.FavoriteDrinksViewModel

@Composable
fun DrinkListScreen(
    viewModel: DrinkListViewModel,
    favoriteDrinksViewModel: FavoriteDrinksViewModel,
    onDrinkCardClicked: (drinkId: String) -> Unit,
    onViewAllClicked: () -> Unit
) {
    val (text, changeSearchBarText) = rememberSaveable {
        mutableStateOf("")
    }
    val viewState = rememberSaveable {
        viewModel.drinkListViewState
    }
    val favoriteDrinks = rememberSaveable {
        favoriteDrinksViewModel.favoriteDrinks
    }

    val onBackPressedInCaseUserNavigatedViaSearch = {
        viewModel.showDrinkSections()
        favoriteDrinksViewModel.showFavoritesSection()
        changeSearchBarText("")
    }

    if (!viewState.value.searchDrinkCards.isNullOrEmpty()) {
        BackPressHandler {
            onBackPressedInCaseUserNavigatedViaSearch.invoke()
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            DrinkListScreenContent(
                categories = null,
                searchDrinkCards = viewState.value.searchDrinkCards,
                drinkSections = viewState.value.drinkSections,
                favoriteDrinks = favoriteDrinks.value,
                searchText = text,
                onSearchTextChanged = {
                    if (it.isNotEmpty()) {
                        favoriteDrinksViewModel.hideFavoriteSection()
                        viewModel.getDrinksBasedOnName(it)
                    } else {
                        viewModel.showDrinkSections()
                        favoriteDrinksViewModel.showFavoritesSection()
                    }
                    changeSearchBarText(it)
                },
                onChipClicked = {
                    viewModel.updateChipSelection(it)
                    viewModel.getDrinkCardsByCategory(it.queryParam)
                },
                onDrinkCardClicked = {
                    onDrinkCardClicked(it)
                },
                onViewAllClicked = onViewAllClicked
            )
            if (viewState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (!viewState.value.error.isNullOrBlank()) {
                Text(
                    text = viewState.value.error!!,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Composable
fun DrinkListScreenContent(
    modifier: Modifier = Modifier,
    categories: List<Category>?,
    searchDrinkCards: List<DrinkCard>?,
    drinkSections: List<DrinkSection>?,
    favoriteDrinks: List<DrinkCard>?,
    searchText: String,
    onSearchTextChanged: (drinkName: String) -> Unit,
    onChipClicked: (category: Category) -> Unit,
    onDrinkCardClicked: (drinkId: String) -> Unit,
    onViewAllClicked: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            SearchBar(
                placeHolder = R.string.search_placeholder,
                value = searchText,
                modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
                onValueChange = {
                    onSearchTextChanged(it)
                }
            )
        }

        if (!favoriteDrinks.isNullOrEmpty() && searchDrinkCards == null) {
            item {
                FavoriteDrinkSection(
                    favoriteDrinks = favoriteDrinks,
                    onClick = {
                        onDrinkCardClicked(it)
                    },
                    onViewAllClicked = onViewAllClicked
                )
            }
        }

        if (categories != null) {
            item {
                HorizontalChipList(
                    categories = categories,
                    modifier = modifier,
                    onChipClicked = {
                        onChipClicked(it)
                    }
                )
            }
        }

        if (searchDrinkCards != null) {
            item {
                DrinkCardsGrid(
                    drinkCards = searchDrinkCards,
                    modifier = modifier,
                    onClick = {
                        onDrinkCardClicked(it)
                    }
                )
            }
        }

        if (drinkSections != null) {
            items(drinkSections) { drinkSection ->
                DrinkSection(
                    modifier = modifier,
                    drinkSection = drinkSection,
                    onDrinkCardClicked = {
                        onDrinkCardClicked(it)
                    }
                )
            }
        }
    }
}