package com.abhinav12k.drunkyard.domain.usecase.getCategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.data.remote.dto.toCategories
import com.abhinav12k.drunkyard.domain.model.Category
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDrinkCategoriesUseCase @Inject constructor(
    private val repository: DrinkRepository
) {

    private val _getCategories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()
    val getCategories: LiveData<Resource<List<Category>>> = _getCategories

    suspend fun fetchCategories() {
        try {
            _getCategories.value = Resource.Loading()
            _getCategories.value = Resource.Success(repository.getAllCategories().toCategories())
        } catch (e: HttpException) {
            _getCategories.value =
                Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!")
        } catch (e: IOException) {
            _getCategories.value =
                Resource.Error(message = "Couldn't reach server. Please check your internet connection")
        }
    }

}