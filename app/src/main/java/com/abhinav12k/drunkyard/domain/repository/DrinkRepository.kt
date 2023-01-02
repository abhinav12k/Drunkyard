package com.abhinav12k.drunkyard.domain.repository

import com.abhinav12k.drunkyard.data.remote.dto.DrinksDto
import com.abhinav12k.drunkyard.data.remote.dto.IngredientsDto
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import kotlinx.coroutines.flow.Flow

interface DrinkRepository {

    suspend fun getAllCategories(): DrinksDto

    suspend fun getDrinksBasedOnCategory(category: String): DrinksDto

    suspend fun getDrinkById(id: String): DrinksDto

    suspend fun getDrinksByName(drinkName: String): DrinksDto

    suspend fun getRandomDrink(): DrinksDto

    suspend fun addDrinkCardToFavorite(drinkCard: DrinkCard)

    suspend fun removeDrinkCardFromFavorite(drinkId: String)

    fun getAllDrinkCardsFromFavorites(): Flow<List<DrinkCard>>

    suspend fun isDrinkCardAddedToFavorites(id: String): Boolean

    suspend fun getIngredientDetailsByName(ingredientName: String): IngredientsDto
}