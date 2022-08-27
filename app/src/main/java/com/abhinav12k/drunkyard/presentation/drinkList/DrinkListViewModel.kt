package com.abhinav12k.drunkyard.presentation.drinkList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.domain.usecase.getCategories.GetDrinkCategoriesUseCase
import com.abhinav12k.drunkyard.domain.usecase.getDrinkByName.GetDrinksByNameUseCase
import com.abhinav12k.drunkyard.domain.usecase.getDrinksByCategory.GetDrinkCardsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val getDrinksByNameUseCase: GetDrinksByNameUseCase,
    private val getDrinkCategoriesUseCase: GetDrinkCategoriesUseCase,
    private val getDrinkCardsByCategoryUseCase: GetDrinkCardsByCategoryUseCase
) : ViewModel() {

    private val _drinkListViewState: MutableState<DrinkListViewState> = mutableStateOf(
        DrinkListViewState()
    )
    val drinkListViewState get() = _drinkListViewState

    private val _drinkCards: MutableState<List<DrinkCard>?> = mutableStateOf(listOf())
    val drinkCards get() = _drinkCards

    private val _drinkCardCategories: MutableState<List<Category>?> = mutableStateOf(listOf())
    val drinkCardCategories get() = _drinkCardCategories

    init {
        getDrinkCategories()
        getDrinkCardsByCategory(null)
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

    fun getDrinkCardsByCategory(category: String?) {
        viewModelScope.launch {
            getDrinkCardsByCategoryUseCase.invoke(category ?: DEFAULT_CATEGORY)
                .collect() { result ->
                    when (result) {
                        is Resource.Success -> {
                            _drinkListViewState.value = DrinkListViewState(isLoading = false)
                            _drinkCards.value = result.data
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
        viewModelScope.launch {
            getDrinksByNameUseCase.invoke(drinkName).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _drinkListViewState.value = DrinkListViewState(isLoading = false)
                        _drinkCards.value = result.data
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

    fun getDrinkDetailsById(drinkId: String) {
        //todo: implement this function
    }

    companion object {
        const val DEFAULT_CATEGORY = "ordinary_drink"
    }
}