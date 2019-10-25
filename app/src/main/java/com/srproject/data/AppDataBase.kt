package com.srproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srproject.data.dao.OrdersDao
import com.srproject.data.dao.ProductsDao
import com.srproject.data.models.Order
import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product

@Database(entities = [Order::class, OrderPosition::class, Product::class], version = 1)
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

    abstract fun getOrdersDao(): OrdersDao
    abstract fun getProductDao(): ProductsDao
}