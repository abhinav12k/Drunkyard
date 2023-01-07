package com.abhinav12k.drunkyard.presentation.favoriteScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.domain.usecase.getDrinkCardsFromFavorite.GetDrinkCardsFromFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDrinksViewModel @Inject constructor(
    private val getAllDrinkCardsFromFavoritesUseCase: GetDrinkCardsFromFavorites,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _favoriteDrinks: MutableState<List<DrinkCard>?> = mutableStateOf(null)
    val favoriteDrinks: State<List<DrinkCard>?> get() = _favoriteDrinks
    private var localFavoriteDrinks: List<DrinkCard>? = null

    init {
        getFavoriteDrinksSection()
    }

    fun hideFavoriteSection() {
        _favoriteDrinks.value = null
    }

    fun showFavoritesSection() {
        if (localFavoriteDrinks == null) {
            viewModelScope.launch {
                getFavoriteDrinksSection()
            }
        } else {
            _favoriteDrinks.value = localFavoriteDrinks
        }
    }

    private fun getFavoriteDrinksSection() {
        if (localFavoriteDrinks == null) {
            viewModelScope.launch {
                getAllDrinkCardsFromFavoritesUseCase.invoke().collect() {
                    saveFavoriteDrinksResponse(it)
                    _favoriteDrinks.value = it
                }
            }
        } else {
            _favoriteDrinks.value = localFavoriteDrinks
        }
    }

    private fun saveFavoriteDrinksResponse(favoriteDrinks: List<DrinkCard>) {
        localFavoriteDrinks = favoriteDrinks
    }
}