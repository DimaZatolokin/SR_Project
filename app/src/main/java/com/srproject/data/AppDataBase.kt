package com.srproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srproject.data.models.Order

@Database(entities = [Order::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(
            context: Context
        ): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    "database-app"
                ).fallbackToDestructiveMigration().build()
                    .also { INSTANCE = it }
            }
        }
    }
}