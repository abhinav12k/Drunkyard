package com.abhinav12k.drunkyard.domain.repository

import com.abhinav12k.drunkyard.data.remote.dto.DrinksDto

interface DrinkRepository {

    suspend fun getAllCategories(): DrinksDto

    suspend fun getDrinksBasedOnCategory(category: String): DrinksDto

    suspend fun getDrinkById(id: String): DrinksDto

    suspend fun getDrinksByName(drinkName: String): DrinksDto

    suspend fun getRandomDrink(): DrinksDto
}