package com.abhinav12k.drunkyard.domain.usecase.getDrinkByCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.data.remote.dto.toDrinkCards
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDrinkCardByCategoryUseCase @Inject constructor(
    private val repository: DrinkRepository
) {

    private val _getDrinkCards: MutableLiveData<Resource<List<DrinkCard>>> = MutableLiveData()
    val getDrinkCards: LiveData<Resource<List<DrinkCard>>> = _getDrinkCards

    suspend fun fetchDrinkCards(category: String) {
        try {
            _getDrinkCards.value = Resource.Loading()
            _getDrinkCards.value =
                Resource.Success(repository.getDrinksBasedOnCategory(category).toDrinkCards())
        } catch (e: HttpException) {
            _getDrinkCards.value =
                Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!")
        } catch (e: IOException) {
            _getDrinkCards.value =
                Resource.Error(message = "Couldn't reach server. Please check your internet connection")
        }
    }
}