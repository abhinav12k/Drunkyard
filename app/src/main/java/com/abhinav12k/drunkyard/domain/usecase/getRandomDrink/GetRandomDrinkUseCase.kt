package com.abhinav12k.drunkyard.domain.usecase.getRandomDrink

import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.data.remote.dto.toDrinkDetail
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRandomDrinkUseCase @Inject constructor(
    private val repository: DrinkRepository
) {
    operator fun invoke(id: String) = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getDrinkById(id).toDrinkDetail()
            if (result == null) {
                emit(Resource.Error(message = "An unexpected error occurred!"))
            } else {
                emit(Resource.Success(result))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)
}