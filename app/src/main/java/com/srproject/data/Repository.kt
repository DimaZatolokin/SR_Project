package com.srproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.srproject.data.models.Order
import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository private constructor(
    private val dbStorage: AppDataBase,
    private val preferences: PreferencesDataSource
) : CoroutineScope {

    override val coroutineContext = Dispatchers.Default

    private val positions = mockPositions()
    private val products = mockProducts()

    suspend fun getActiveOrders(): LiveData<List<Order>> {
        return dbStorage.getOrdersDao().getActiveOrders()
    }

    suspend fun getProductById(id: Long): Product? {
        return dbStorage.getProductDao().getProductById(id)
    }

    suspend fun getOrderById(id: Long): Order? {
        return dbStorage.getOrdersDao().getOrderById(id)
    }

    suspend fun getOrderPositionsByOrderId(id: Long): List<OrderPosition> {
        return dbStorage.getOrdersDao().getOrderPositions(id)
    }

    fun updateOrder(order: Order) {
        launch {
            //TODO
        }
    }

    suspend fun getProducts(): LiveData<List<Product>> {
        return dbStorage.getProductDao().getProducts()
    }

    fun createOrder(order: Order) {
        launch {
            dbStorage.getOrdersDao().saveOrder(order)
        }
    }

    fun createProduct(product: Product) {
        launch {
            dbStorage.getProductDao().insertProduct(product)
        }
    }

    fun updateProduct(product: Product) {
        launch {
            //TODO
        }
    }

    fun deleteProduct(id: Long) {
        launch {
            //TODO
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun init(
            dbStorage: AppDataBase,
            preferences: PreferencesDataSource
        ) {
            if (INSTANCE == null) {
                INSTANCE = Repository(dbStorage, preferences)
            }
        }

        fun getInstance(): Repository {
            return synchronized(this) {
                INSTANCE?.let {
                    return INSTANCE as Repository
                } ?: throw RuntimeException("need to init Repository")
            }
        }
    }
}