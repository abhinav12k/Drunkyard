package com.abhinav12k.drunkyard.presentation.drinkDetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.domain.usecase.getDrinkDetail.GetDrinkDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailViewModel @Inject constructor(
    private val getDrinkDetailUseCase: GetDrinkDetailUseCase
) : ViewModel() {

    private val _drinkDetailViewState: MutableState<DrinkDetailViewState> =
        mutableStateOf(DrinkDetailViewState())
    val drinkDetailViewState: State<DrinkDetailViewState> get() = _drinkDetailViewState

    fun getDrinkDetailsById(drinkId: String) {
        viewModelScope.launch {
            getDrinkDetailUseCase.invoke(drinkId).collect() { result ->
                when (result) {
                    is Resource.Success -> {
                        _drinkDetailViewState.value =
                            DrinkDetailViewState(drinkDetail = result.data)
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

}