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
import com.abhinav12k.drunkyard.domain.usecase.getDrinksByCategory.GetDrinkCardsByCategoryUseCase
import com.abhinav12k.drunkyard.presentation.drinkList.model.DrinkSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val getDrinksByNameUseCase: GetDrinksByNameUseCase,
    private val getDrinkCategoriesUseCase: GetDrinkCategoriesUseCase,
    private val getDrinkCardsByCategoryUseCase: GetDrinkCardsByCategoryUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _drinkListViewState: MutableState<DrinkListViewState> = mutableStateOf(
        DrinkListViewState()
    )
    val drinkListViewState: State<DrinkListViewState> get() = _drinkListViewState

    private var localDrinkSections: List<DrinkSection>? = null
    private var getDrinksByNameJob: Job? = null

    init {
        getDrinkSections()
    }

    fun showDrinkSections() {
        getDrinksByNameJob?.cancel()
        if (localDrinkSections == null) {
            viewModelScope.launch {
                getDrinkSections()
            }
        } else {
            _drinkListViewState.value = DrinkListViewState(drinkSections = localDrinkSections)
        }
    }

    private fun saveDrinkSectionsResponse(drinkSections: List<DrinkSection>) {
        localDrinkSections = drinkSections
    }

    private fun getDrinkCategories() {
        viewModelScope.launch {
            getDrinkCategoriesUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _drinkListViewState.value = DrinkListViewState(categories = result.data)
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

    private fun getDrinkSections() = viewModelScope.launch {
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
        _drinkListViewState.value = DrinkListViewState(drinkSections = drinkSections)
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
                            _drinkListViewState.value =
                                DrinkListViewState(searchDrinkCards = result.data)
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


    fun getDrinksBasedOnName(drinkName: String) {
        getDrinksByNameJob?.cancel("Another search request made by user!")
        getDrinksByNameJob = viewModelScope.launch(dispatcher) {
            getDrinksByNameUseCase.invoke(drinkName).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _drinkListViewState.value =
                            DrinkListViewState(searchDrinkCards = result.data)
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
        drinkListViewState.value.categories?.forEach {
            if (category.categoryName == it.categoryName) {
                updatedCategories.add(it.copy(isSelected = true))
            } else {
                updatedCategories.add(it.copy(isSelected = false))
            }
        }
        _drinkListViewState.value = drinkListViewState.value.copy(categories = updatedCategories)
    }

    companion object {
        const val DEFAULT_CATEGORY = "ordinary_drink"
    }
}