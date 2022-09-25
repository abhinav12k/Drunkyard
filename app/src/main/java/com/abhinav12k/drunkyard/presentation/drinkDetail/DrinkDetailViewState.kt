package com.abhinav12k.drunkyard.presentation.drinkDetail

import android.os.Parcelable
import com.abhinav12k.drunkyard.domain.model.DrinkDetail
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinkDetailViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val drinkDetail: DrinkDetail? = null
): Parcelable