package com.abhinav12k.drunkyard.presentation.drinkList.model

import android.os.Parcelable
import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinkSection(
    val category: Category,
    val list: List<DrinkCard>
): Parcelable