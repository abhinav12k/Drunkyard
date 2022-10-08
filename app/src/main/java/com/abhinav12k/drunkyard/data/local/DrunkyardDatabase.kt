package com.abhinav12k.drunkyard.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhinav12k.drunkyard.data.local.dao.DrinkCardDao
import com.abhinav12k.drunkyard.domain.model.DrinkCard

@Database(entities = [DrinkCard::class], version = 1)
abstract class DrunkyardDatabase : RoomDatabase() {

    abstract fun drinkCardDao(): DrinkCardDao

    companion object {
        private const val DB_NAME = "drunkyard_db"

        @Volatile
        private var INSTANCE: DrunkyardDatabase? = null

        fun getInstance(context: Context): DrunkyardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrunkyardDatabase::class.java, DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}