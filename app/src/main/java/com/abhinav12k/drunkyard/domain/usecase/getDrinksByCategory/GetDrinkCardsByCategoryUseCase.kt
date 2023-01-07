package com.abhinav12k.drunkyard.domain.usecase.getDrinksByCategory

import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.data.remote.dto.toDrinkCards
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDrinkCardsByCategoryUseCase @Inject constructor(
    private val repository: DrinkRepository
) {

    operator fun invoke(category: String) = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repository.getDrinksBasedOnCategory(category).toDrinkCards()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)
}