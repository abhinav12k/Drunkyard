package com.abhinav12k.drunkyard.domain.usecase.getDrinkByName

import com.abhinav12k.drunkyard.common.Resource
import com.abhinav12k.drunkyard.data.remote.dto.toDrinkCards
import com.abhinav12k.drunkyard.domain.model.DrinkCard
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDrinksByNameUseCase @Inject constructor(
    private val repository: DrinkRepository
) {
    operator fun invoke(name: String) = flow {
        try {
            emit(Resource.Loading())
            repository.getDrinksByName(name).toDrinkCards().let {
                if (it.isNotEmpty()) emit(Resource.Success(it))
                else emit(Resource.Error(message = "Sorry we don't have any drink with $name :("))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Please check your internet connection"))
        }
    }.flowOn(Dispatchers.IO)

}