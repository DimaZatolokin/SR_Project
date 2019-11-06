package com.srproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.srproject.data.dao.OrdersDao
import com.srproject.data.dao.ProductsDao
import com.srproject.data.models.Order
import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product

@Database(entities = [Order::class, OrderPosition::class, Product::class], version = 2)
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
                ).addMigrations(Migration1to2())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    abstract fun getOrdersDao(): OrdersDao
    abstract fun getProductDao(): ProductsDao
}

private class Migration1to2 : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE OrderPosition ADD COLUMN done INTEGER DEFAULT 0 NOT NULL")
    }

}