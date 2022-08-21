package com.abhinav12k.drunkyard.domain.usecase.getRandomDrink

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.data.remote.dto.toDrinkDetail
import com.abhinav12k.drunkyard.domain.model.DrinkDetail
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRandomDrinkUseCase @Inject constructor(
    private val repository: DrinkRepository
) {
    private val _getRandomDrinkDetail = MutableLiveData<Resource<DrinkDetail>>()
    val getRandomDrinkDetail: LiveData<Resource<DrinkDetail>> = _getRandomDrinkDetail

    suspend fun getDrinkDetail(id: String) {
        try {
            _getRandomDrinkDetail.value = Resource.Loading()
            val result = repository.getDrinkById(id).toDrinkDetail()
            if (result == null) {
                _getRandomDrinkDetail.value =
                    Resource.Error(message = "An unexpected error occurred!")
            } else {
                _getRandomDrinkDetail.value = Resource.Success(result)
            }
        } catch (e: HttpException) {
            _getRandomDrinkDetail.value =
                Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!")
        } catch (e: IOException) {
            _getRandomDrinkDetail.value =
                Resource.Error(message = "Couldn't reach server. Please check your internet connection")
        }
    }
}