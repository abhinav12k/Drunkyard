package com.abhinav12k.drunkyard.presentation.drinkList

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinkListViewState(
    val isLoading: Boolean = false,
    val error: String? = null
): Parcelable