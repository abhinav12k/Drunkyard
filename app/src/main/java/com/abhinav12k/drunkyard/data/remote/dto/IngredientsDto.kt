package com.abhinav12k.drunkyard.data.remote.dto

import android.util.Log
import com.abhinav12k.drunkyard.domain.model.IngredientDetail
import com.google.gson.annotations.SerializedName

data class IngredientsDto(
    @SerializedName("ingredients")
    val ingredients: List<IngredientDto>? = null
) {
    fun toIngredientDetail(): IngredientDetail? = ingredients?.getOrNull(0)?.toIngredientDetail()
}

data class IngredientDto(
    @SerializedName("idIngredient")
    val id: String? = null,
    @SerializedName("strIngredient")
    val name: String? = null,
    @SerializedName("strDescription")
    val description: String? = null,
    @SerializedName("strType")
    val type: String? = null,
    @SerializedName("strAlcohol")
    val alcohol: String? = null, //--- Yes in case it is alcohol
    @SerializedName("strABV")
    val abv: String? = null
)

fun IngredientDto.toIngredientDetail(): IngredientDetail? {
    return try {
        IngredientDetail(
            id = this.id!!,
            name = this.name!!,
            description = this.description ?: "",
            type = this.type ?: "Not Specified",
            isAlcohol = this.alcohol?.lowercase() == "yes",
            abv = this.abv
        )
    } catch (e: Exception) {
        Log.d("Conversion Error:", "Failed to convert IngredientDto to IngredientDetail")
        null
    }
}