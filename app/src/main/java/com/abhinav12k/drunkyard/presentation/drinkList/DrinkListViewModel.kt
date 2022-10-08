package com.abhinav12k.drunkyard.presentation.drinkList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.domain.usecase.getCategories.GetDrinkCategoriesUseCase
import com.abhinav12k.drunkyard.domain.usecase.getDrinkByName.GetDrinksByNameUseCase
import com.abhinav12k.drunkyard.domain.usecase.getDrinkCardsFromFavorite.GetDrinkCardsFromFavorites
import com.abhinav12k.drunkyard.domain.usecase.getDrinksByCategory.GetDrinkCardsByCategoryUseCase
import com.abhinav12k.drunkyard.presentation.drinkDetail.DrinkDetailViewState
import com.abhinav12k.drunkyard.presentation.drinkList.model.DrinkSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val getDrinksByNameUseCase: GetDrinksByNameUseCase,
    private val getDrinkCategoriesUseCase: GetDrinkCategoriesUseCase,
    private val getDrinkCardsByCategoryUseCase: GetDrinkCardsByCategoryUseCase,
    private val getAllDrinkCardsFromFavoritesUseCase: GetDrinkCardsFromFavorites,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _drinkListViewState: MutableState<DrinkListViewState> = mutableStateOf(
        DrinkListViewState()
    )
    val drinkListViewState: State<DrinkListViewState> get() = _drinkListViewState

    private val _searchDrinkCards: MutableState<List<DrinkCard>?> = mutableStateOf(listOf())
    val searchDrinkCards: State<List<DrinkCard>?> get() = _searchDrinkCards

    private val _drinkCardCategories: MutableState<List<Category>?> = mutableStateOf(listOf())
    val drinkCardCategories: State<List<Category>?> get() = _drinkCardCategories

    private val _drinkSections: MutableState<List<DrinkSection>?> = mutableStateOf(listOf())
    val drinkSections: State<List<DrinkSection>?> get() = _drinkSections

    private var localDrinkSections: List<DrinkSection>? = null

    private val _allFavoriteDrinks: MutableState<List<DrinkCard>> = mutableStateOf(listOf())
    val allFavoriteDrinks: State<List<DrinkCard>> get() = _allFavoriteDrinks

    init {
        getDrinkCategoriesInit()
        getAllDrinkCardsFromFavorites()
    }

    fun removeDrinkSections() {
        _drinkSections.value = null
    }

    fun removeSearchedDrinkCards() {
        _searchDrinkCards.value = null
    }

    fun showDrinkSections() {
        getDrinksByNameJob?.cancel()
        if (localDrinkSections == null) {
            getDrinkCategoriesInit()
            return
        }
        _drinkSections.value = localDrinkSections
        _drinkListViewState.value = DrinkListViewState(isLoading = false)
    }

    private fun saveDrinkSectionsResponse(drinkSections: List<DrinkSection>) {
        localDrinkSections = drinkSections
    }

    private fun getDrinkCategories() {
        viewModelScope.launch {
            getDrinkCategoriesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _drinkListViewState.value = DrinkListViewState(isLoading = false)
                        _drinkCardCategories.value = result.data
                    }
                    is Resource.Error -> {
                        _drinkListViewState.value = DrinkListViewState(error = result.message)
                    }
                    is Resource.Loading -> {
                        _drinkListViewState.value = DrinkListViewState(isLoading = true)
                    }
                }
            }
        }
    }

    private fun getDrinkCategoriesInit() {
        viewModelScope.launch {
            getDrinkCategoriesSuspend()
        }
    }

    private suspend fun getDrinkCategoriesSuspend() {
        getDrinkCategoriesUseCase.invoke().collect() { result ->
            when (result) {
                is Resource.Success -> getDrinksByCategories(result.data)
                is Resource.Error -> _drinkListViewState.value =
                    DrinkListViewState(error = result.message)
                is Resource.Loading -> _drinkListViewState.value =
                    DrinkListViewState(isLoading = true)
            }
        }
    }

    private suspend fun getDrinksByCategories(categories: List<Category>?) {
        if (categories == null) return
        val drinkSections = mutableListOf<DrinkSection>()
        categories.forEach { category ->
            val cards = getDrinkCardsByCategorySuspend(category.queryParam)
            if (!cards.isNullOrEmpty()) {
                drinkSections.add(
                    DrinkSection(
                        category,
                        cards
                    )
                )
            }
        }
        saveDrinkSectionsResponse(drinkSections)
        _drinkSections.value = drinkSections
        _drinkListViewState.value = DrinkListViewState(isLoading = false)
    }

    private suspend fun getDrinkCardsByCategorySuspend(category: String): List<DrinkCard>? =
        withContext(viewModelScope.coroutineContext) {
            var drinkCards: List<DrinkCard>? = null
            getDrinkCardsByCategoryUseCase.invoke(category).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        drinkCards = result.data
                    }
                    else -> {}
                }
            }
            drinkCards
        }

    fun getDrinkCardsByCategory(category: String?) {
        viewModelScope.launch {
            getDrinkCardsByCategoryUseCase.invoke(category ?: DEFAULT_CATEGORY)
                .collect() { result ->
                    when (result) {
                        is Resource.Success -> {
                            _drinkListViewState.value = DrinkListViewState(isLoading = false)
                            _searchDrinkCards.value = result.data
                        }
                        is Resource.Error -> {
                            _drinkListViewState.value = DrinkListViewState(error = result.message)
                        }
                        is Resource.Loading -> {
                            _drinkListViewState.value = DrinkListViewState(isLoading = true)
                        }
                    }
                }
        }
    }

    private var getDrinksByNameJob: Job? = null

    fun getDrinksBasedOnName(drinkName: String) {
        getDrinksByNameJob?.cancel("Another search request made by user!")
        getDrinksByNameJob = viewModelScope.launch(dispatcher) {
            getDrinksByNameUseCase.invoke(drinkName).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _drinkListViewState.value = DrinkListViewState(isLoading = false)
                        _searchDrinkCards.value = result.data
                    }
                    is Resource.Error -> {
                        _drinkListViewState.value = DrinkListViewState(error = result.message)
                    }
                    is Resource.Loading -> {
                        _drinkListViewState.value = DrinkListViewState(isLoading = true)
                    }
                }
            }
        }
        _drinkListViewState.value = DrinkListViewState(isLoading = true)
    }

    fun updateChipSelection(category: Category) {
        val updatedCategories = mutableListOf<Category>()
        drinkCardCategories.value?.forEach {
            if (category.categoryName == it.categoryName) {
                updatedCategories.add(it.copy(isSelected = true))
            } else {
                updatedCategories.add(it.copy(isSelected = false))
            }
        }
        _drinkCardCategories.value = updatedCategories
    }

    fun getAllDrinkCardsFromFavorites() {
        viewModelScope.launch {
            getAllDrinkCardsFromFavoritesUseCase.invoke().collect() {
                _allFavoriteDrinks.value = it
            }
        }
    }

    companion object {
        const val DEFAULT_CATEGORY = "ordinary_drink"
    }
}