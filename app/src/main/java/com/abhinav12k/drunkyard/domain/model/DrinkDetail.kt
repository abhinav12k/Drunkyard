package com.abhinav12k.drunkyard.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinkDetail(
    val id: String,
    val drinkName: String,
    val thumbnail: String,
    val image: String,
    val alcoholic: String,
    val glass: String,
    val instructions: String,
    val ingredients: List<Ingredient>
): Parcelable

@Parcelize
data class Ingredient(
    val name: String,
    val amount: String
): Parcelable
