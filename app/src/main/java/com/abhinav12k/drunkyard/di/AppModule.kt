package com.abhinav12k.drunkyard.di

import android.content.Context
import com.abhinav12k.drunkyard.common.Constants
import com.abhinav12k.drunkyard.data.remote.CocktailApi
import com.abhinav12k.drunkyard.data.repository.DrinkRepositoryImpl
import com.abhinav12k.drunkyard.domain.repository.DrinkRepository
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCocktailApi(@ApplicationContext appContext: Context): CocktailApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(appContext))
            .build()
        return Retrofit.Builder()
            .client(client)
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