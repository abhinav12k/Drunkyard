package com.abhinav12k.drunkyard.presentation.drinkList.model

import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.model.DrinkCard

data class DrinkSection(
    val category: Category,
    val list: List<DrinkCard>
)