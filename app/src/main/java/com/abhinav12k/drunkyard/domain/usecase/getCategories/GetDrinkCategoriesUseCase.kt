package com.abhinav12k.drunkyard.domain.usecase.getCategories

import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.data.remote.dto.toCategories
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDrinkCategoriesUseCase @Inject constructor(
    private val repository: DrinkRepository
) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getAllCategories().toCategories()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }

}