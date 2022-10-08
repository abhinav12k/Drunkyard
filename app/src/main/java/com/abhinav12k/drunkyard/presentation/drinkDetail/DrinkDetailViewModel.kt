package com.abhinav12k.drunkyard.presentation.drinkDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.domain.model.DrinkDetail
import com.abhinav12k.drunkyard.domain.usecase.addDrinkCardToFavorite.AddDrinkCardToFavoriteUseCase
import com.abhinav12k.drunkyard.domain.usecase.getDrinkCardsFromFavorite.GetDrinkCardsFromFavorites
import com.abhinav12k.drunkyard.domain.usecase.getDrinkDetail.GetDrinkDetailUseCase
import com.abhinav12k.drunkyard.domain.usecase.isDrinkCardFavorite.IsDrinkCardFavoriteUseCase
import com.abhinav12k.drunkyard.domain.usecase.removeDrinkCardFromFavorite.RemoveDrinkCardFromFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(
    private val getDrinkDetailUseCase: GetDrinkDetailUseCase,
    private val removeDrinkCardFromFavoriteUseCase: RemoveDrinkCardFromFavorite,
    private val addDrinkCardToFavoriteUseCase: AddDrinkCardToFavoriteUseCase,
    private val isDrinkCardFavoriteUseCase: IsDrinkCardFavoriteUseCase
) : ViewModel() {

    private val _drinkDetailViewState: MutableState<DrinkDetailViewState> =
        mutableStateOf(DrinkDetailViewState())
    val drinkDetailViewState: State<DrinkDetailViewState> get() = _drinkDetailViewState

    private val _isDrinkAddedAsFavorite: MutableState<Boolean> = mutableStateOf(false)
    val isDrinkAddedAsFavorite: State<Boolean> = _isDrinkAddedAsFavorite

    private var currentDrinkDetail: DrinkDetail? = null

    private fun getDrinkCardFromDrinkDetail(): DrinkCard {
        return DrinkCard(
            currentDrinkDetail?.id ?: "",
            currentDrinkDetail?.drinkName ?: "Unknown drink",
            currentDrinkDetail?.thumbnail ?: "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg/preview",
            currentDrinkDetail?.image ?: "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg/preview"
        )
    }

    fun getDrinkDetailsById(drinkId: String) {
        viewModelScope.launch {
            getDrinkDetailUseCase.invoke(drinkId).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _drinkDetailViewState.value =
                            DrinkDetailViewState(drinkDetail = result.data)
                        currentDrinkDetail = result.data
                    }
                    is Resource.Error -> {
                        _drinkDetailViewState.value = DrinkDetailViewState(error = result.message)
                    }
                    is Resource.Loading -> {
                        _drinkDetailViewState.value = DrinkDetailViewState(isLoading = true)
                    }
                }
            }
        }
    }

    fun addDrinkCardToFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            addDrinkCardToFavoriteUseCase.invoke(getDrinkCardFromDrinkDetail()).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _isDrinkAddedAsFavorite.value = true
                    }
                    is Resource.Error -> {
                        _drinkDetailViewState.value = DrinkDetailViewState(error = result.message)
                    }
                    is Resource.Loading -> {
                        //Room transaction under process
                    }
                }
            }
        }
    }

    fun removeDrinkCardFromFavorite(drinkId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeDrinkCardFromFavoriteUseCase.invoke(drinkId).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _isDrinkAddedAsFavorite.value = false
                    }
                    is Resource.Error -> {
                        _drinkDetailViewState.value = DrinkDetailViewState(error = result.message)
                    }
                    is Resource.Loading -> {
                        //Room transaction under process
                    }
                }
            }
        }
    }

    fun isDrinkCardAddedToFavorites(id: String) {
        viewModelScope.launch {
            isDrinkCardFavoriteUseCase.invoke(id).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _isDrinkAddedAsFavorite.value = result.data ?: false
                    }
                    is Resource.Error -> {
                        _drinkDetailViewState.value = DrinkDetailViewState(error = result.message)
                    }
                    is Resource.Loading -> {
                        //Room transaction under process
                    }
                }
            }
        }
    }
}