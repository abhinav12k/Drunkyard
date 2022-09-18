package com.abhinav12k.drunkyard.presentation.drinkDetail

import com.abhinav12k.drunkyard.domain.model.DrinkDetail

data class DrinkDetailViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val drinkDetail: DrinkDetail? = null
)