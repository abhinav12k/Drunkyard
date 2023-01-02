package com.abhinav12k.drunkyard.domain.model

data class IngredientDetail(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val isAlcohol: Boolean,
    val abv: String?
) {
    fun getIngredientImage(type: Dimension): String {
        return when(type) {
            Dimension.SMALL -> "https://www.thecocktaildb.com/images/ingredients/${name.split(" ").last()}-Small.png"
            Dimension.MEDIUM -> "https://www.thecocktaildb.com/images/ingredients/${name.split(" ").last()}-Medium.png"
            Dimension.LARGE -> "https://www.thecocktaildb.com/images/ingredients/${name.split(" ").last()}.png"
        }
    }

    enum class Dimension {
        SMALL,
        MEDIUM,
        LARGE
    }
}