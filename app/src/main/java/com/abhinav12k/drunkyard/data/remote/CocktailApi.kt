package com.abhinav12k.drunkyard.data.remote

import com.abhinav12k.drunkyard.data.remote.dto.DrinksDto
import com.abhinav12k.drunkyard.data.remote.dto.IngredientsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface CocktailApi {

    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getAllCategories(): DrinksDto

    @GET("api/json/v1/1/list.php?g=list")
    suspend fun getGlassFilters(): DrinksDto

    @GET("api/json/v1/1/list.php?i=list")
    suspend fun getIngredientsFilters(): DrinksDto

    @GET("api/json/v1/1/list.php?a=list")
    suspend fun getAlcoholicFilters(): DrinksDto

    @GET("api/json/v1/1/filter.php")
    suspend fun getDrinksBasedOnCategory(@Query("c") category: String): DrinksDto

    @GET("api/json/v1/1/lookup.php")
    suspend fun getDrinkById(@Query("i") id: String): DrinksDto

    @GET("api/json/v1/1/search.php")
    suspend fun getDrinksByName(@Query("s") drinkName: String): DrinksDto

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomDrink(): DrinksDto

    @GET("api/json/v1/1/search.php")
    suspend fun getIngredientDetailsByName(@Query("i") ingredient: String): IngredientsDto
}