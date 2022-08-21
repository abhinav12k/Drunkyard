package com.abhinav12k.drunkyard.data.remote.dto


import android.util.Log
import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.domain.model.DrinkDetail
import com.google.gson.annotations.SerializedName

data class DrinksDto(
    @SerializedName("drinks")
    val drinks: List<Drink>
) {
    fun getDrinkDetail(): Drink = drinks[0]
}

fun DrinksDto.toCategories(): List<Category> {
    val categories = mutableListOf<Category>()
    drinks.forEach { drink ->
        val category = try {
            Category(drink.categoryName!!, drink.getDrinkNameForQueryParam()!!)
        } catch (e: Exception) {
            Log.d("Conversion Error:", "Failed to convert DrinkDto to Category for $drink")
            null
        }
        category?.let { categories.add(it) }
    }
    return categories
}

fun DrinksDto.toDrinkCards(): List<DrinkCard> {
    val drinkCards = mutableListOf<DrinkCard>()
    drinks.forEach { drink ->
        val drinkCard = try {
            DrinkCard(
                drink.drinkId!!,
                drink.drinkName!!,
                drink.getDrinkThumbnail()!!,
                drink.getDrinkImage()!!
            )

        } catch (e: Exception) {
            Log.d("Conversion Error:", "Failed to convert DrinkDto to DrinkCard for $drink")
            null
        }
        drinkCard?.let { drinkCards.add(it) }
    }
    return drinkCards
}

fun DrinksDto.toDrinkDetail(): DrinkDetail? {
    return try {
        val drink = getDrinkDetail()
        DrinkDetail(
            id = drink.drinkId!!,
            drinkName = drink.drinkName!!,
            thumbnail = drink.getDrinkThumbnail()!!,
            image = drink.getDrinkImage()!!,
            alcoholic = drink.alcoholic!!,
            glass = drink.glass ?: "",
            instructions = drink.instructions!!,
            ingredients = drink.getIngredients()
        )
    } catch (e: Exception) {
        Log.d("Conversion Error:", "Failed to convert DrinkDto to DrinkDetail")
        null
    }
}