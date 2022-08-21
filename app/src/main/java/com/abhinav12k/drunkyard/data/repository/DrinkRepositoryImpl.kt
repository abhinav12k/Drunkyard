package com.abhinav12k.drunkyard.data.repository

import com.abhinav12k.drunkyard.data.remote.CocktailApi
import com.abhinav12k.drunkyard.data.remote.dto.DrinksDto
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import javax.inject.Inject

class DrinkRepositoryImpl @Inject constructor(
    private val api: CocktailApi
) : DrinkRepository {

    override suspend fun getAllCategories(): DrinksDto {
        return api.getAllCategories()
    }

    override suspend fun getDrinksBasedOnCategory(category: String): DrinksDto {
        return api.getDrinksBasedOnCategory(category)
    }

    override suspend fun getDrinkById(id: String): DrinksDto {
        return api.getDrinkById(id)
    }

    override suspend fun getDrinksByName(drinkName: String): DrinksDto {
        return api.getDrinksByName(drinkName)
    }

    override suspend fun getRandomDrink(): DrinksDto {
        return api.getRandomDrink()
    }
}