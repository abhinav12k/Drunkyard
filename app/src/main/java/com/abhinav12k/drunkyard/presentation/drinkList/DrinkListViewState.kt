package com.abhinav12k.drunkyard.presentation.drinkList

import android.os.Parcelable
import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.presentation.drinkList.model.DrinkSection
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinkListViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchDrinkCards: List<DrinkCard>? = null,
    val categories: List<Category>? = null,
    val drinkSections: List<DrinkSection>? = null
) : Parcelable