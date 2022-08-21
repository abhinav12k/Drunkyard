package com.abhinav12k.drunkyard.domain.usecase.getDrinkDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.data.remote.dto.toDrinkDetail
import com.abhinav12k.drunkyard.domain.model.DrinkDetail
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDrinkDetailUseCase @Inject constructor(
    private val repository: DrinkRepository
) {

    private val _getDrinkDetail = MutableLiveData<Resource<DrinkDetail>>()
    val getDrinkDetail: LiveData<Resource<DrinkDetail>> = _getDrinkDetail

    suspend fun getDrinkDetail(id: String) {
        try {
            _getDrinkDetail.value = Resource.Loading()
            val result = repository.getDrinkById(id).toDrinkDetail()
            if (result == null) {
                _getDrinkDetail.value =
                    Resource.Error(message = "An unexpected error occurred!")
            } else {
                _getDrinkDetail.value = Resource.Success(result)
            }
        } catch (e: HttpException) {
            _getDrinkDetail.value =
                Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!")
        } catch (e: IOException) {
            _getDrinkDetail.value =
                Resource.Error(message = "Couldn't reach server. Please check your internet connection")
        }
    }

}