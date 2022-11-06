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

@Composable
fun DrinkListScreen(
    viewModel: DrinkListViewModel,
    onDrinkCardClicked: (drinkId: String) -> Unit
) {
    val searchDrinkCards = rememberSaveable {
        viewModel.searchDrinkCards
    }
    val drinkSections = rememberSaveable {
        viewModel.drinkSections
    }
    val (text, changeSearchBarText) = rememberSaveable {
        mutableStateOf("")
    }
    val viewState = rememberSaveable(inputs = arrayOf(searchDrinkCards, drinkSections)) {
        viewModel.drinkListViewState
    }
    val favoriteDrinks = viewModel.allFavoriteDrinks

    val onBackPressedInCaseUserNavigatedViaSearch = {
        viewModel.removeSearchedDrinkCards()
        viewModel.showDrinkSections()
        changeSearchBarText("")
    }

    if (!searchDrinkCards.value.isNullOrEmpty()) {
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
                drinkCards = searchDrinkCards.value,
                drinkSections = drinkSections.value,
                favoriteDrinks = favoriteDrinks.value,
                searchText = text,
                onSearchTextChanged = {
                    if (it.isNotEmpty()) {
                        viewModel.getDrinksBasedOnName(it)
                        viewModel.removeDrinkSections()
                    } else {
                        viewModel.showDrinkSections()
                        viewModel.removeSearchedDrinkCards()
                    }
                    changeSearchBarText(it)
                },
                onChipClicked = {
                    viewModel.updateChipSelection(it)
                    viewModel.getDrinkCardsByCategory(it.queryParam)
                },
                onDrinkCardClicked = {
                    onDrinkCardClicked(it)
                }
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

@Composable
fun DrinkListScreenContent(
    modifier: Modifier = Modifier,
    categories: List<Category>?,
    drinkCards: List<DrinkCard>?,
    drinkSections: List<DrinkSection>?,
    favoriteDrinks: List<DrinkCard>?,
    searchText: String,
    onSearchTextChanged: (drinkName: String) -> Unit,
    onChipClicked: (category: Category) -> Unit,
    onDrinkCardClicked: (drinkId: String) -> Unit
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

        if (favoriteDrinks != null) {
            item {
                FavoriteDrinkSection(
                    favoriteDrinks = favoriteDrinks,
                    onClick = {
                        onDrinkCardClicked(it)
                    }
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

        if (drinkCards != null) {
            item {
                DrinkCardsGrid(
                    drinkCards = drinkCards,
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