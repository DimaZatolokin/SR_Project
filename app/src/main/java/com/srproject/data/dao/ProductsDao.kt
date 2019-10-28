package com.srproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.srproject.data.models.Product

@Dao
abstract class ProductsDao {

    @Query("SELECT * FROM Product")
    abstract fun getProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE id == :id")
    abstract fun getProductById(id: Long): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProduct(product: Product)

    @Query("DELETE FROM Product WHERE id == :id")
    abstract fun deleteProduct(id: Long)
}