package com.abhinav12k.drunkyard.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DrinkCard(
    val id: String,
    val drinkName: String,
    val thumbnail: String,
    val image: String
): Parcelable {
    companion object {
        fun mock() = DrinkCard(
            "1",
            "Cocktail",
            "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg/preview",
            "https://www.thecocktaildb.com/images/media/drink/vrwquq1478252802.jpg"
        )
    }
}