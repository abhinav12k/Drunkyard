package com.abhinav12k.drunkyard.presentation.ingredient

import com.abhinav12k.drunkyard.domain.model.IngredientDetail

data class IngredientBottomSheetViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val ingredientDetail: IngredientDetail? = null
)
