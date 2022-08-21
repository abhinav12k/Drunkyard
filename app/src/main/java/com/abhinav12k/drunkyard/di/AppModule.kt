package com.abhinav12k.drunkyard.di

import com.abhinav12k.drunkyard.common.Constants
import com.abhinav12k.drunkyard.data.remote.CocktailApi
import com.abhinav12k.drunkyard.data.repository.DrinkRepositoryImpl
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCocktailApi(): CocktailApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDrinkRepository(api: CocktailApi): DrinkRepository {
        return DrinkRepositoryImpl(api)
    }

}