package com.abhinav12k.drunkyard.data.remote

import com.abhinav12k.drunkyard.data.remote.dto.DrinksDto
import retrofit2.http.GET
import retrofit2.http.Path


interface CocktailApi {

    @GET("api/json/v1/1/list.php?c=list")
    suspend fun getAllCategories(): DrinksDto

    @GET("api/json/v1/1/list.php?g=list")
    suspend fun getGlassFilters(): DrinksDto

    @GET("api/json/v1/1/list.php?i=list")
    suspend fun getIngredientsFilters(): DrinksDto

    @GET("api/json/v1/1/list.php?a=list")
    suspend fun getAlcoholicFilters(): DrinksDto

    @GET("api/json/v1/1/filter.php?c={category}")
    suspend fun getDrinksBasedOnCategory(@Path("category") category: String): DrinksDto

    @GET("api/json/v1/1/lookup.php?i={id}")
    suspend fun getDrinkById(@Path("id") id: String): DrinksDto

    @GET("api/json/v1/1/search.php?s={drinkName}")
    suspend fun getDrinksByName(@Path("drinkName") drinkName: String): DrinksDto

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomDrink(): DrinksDto
}