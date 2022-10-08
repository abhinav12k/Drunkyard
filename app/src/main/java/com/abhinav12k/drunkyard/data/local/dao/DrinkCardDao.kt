package com.abhinav12k.drunkyard.data.local.dao

import androidx.room.*
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkCardDao {

    @Query("SELECT * FROM drink_cards")
    fun getAllDrinkCards(): Flow<List<DrinkCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDrinkCardToFavorites(drinkCard: DrinkCard)

    @Query("DELETE FROM drink_cards WHERE id = :id")
    suspend fun removeDrinkCardFromFavorites(id: String)

    @Query("SELECT * FROM drink_cards WHERE id = :id")
    suspend fun isDrinkCardAddedToFavorite(id: String): DrinkCard?
}