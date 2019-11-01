package com.srproject.data

import androidx.lifecycle.LiveData
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

    suspend fun getAllOrders(): LiveData<List<Order>> {
        return dbStorage.getOrdersDao().getAllOrders()
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
            dbStorage.getOrdersDao().saveOrder(order)
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
            dbStorage.getProductDao().insertProduct(product)
        }
    }

    fun deleteProduct(id: Long) {
        launch {
            dbStorage.getProductDao().deleteProduct(id)
        }
    }

    suspend fun getProductUsagesCount(id: Long): Int {
        return dbStorage.getProductDao().getProductUsagesCount(id)
    }

    fun deleteOrder(id: Long) {
        launch {
            dbStorage.getOrdersDao().deleteOrder(id)
        }
    }

    suspend fun getTotalSoldSum(): Int {
        return dbStorage.getOrdersDao().getTotalSoldSum()
    }

    suspend fun getFilteredOrders(notDone: Boolean, notPaid: Boolean): LiveData<List<Order>> {
        return if (notDone && notPaid) dbStorage.getOrdersDao().getNotPaidAndNotDoneActiveOrders()
        else if (notDone) dbStorage.getOrdersDao().getNotDoneActiveOrders()
        else if (notPaid) dbStorage.getOrdersDao().getNotPaidActiveOrders()
        else dbStorage.getOrdersDao().getActiveOrders()
    }

    suspend fun getConsumers(): List<String> {
        return dbStorage.getOrdersDao().getAllConsumers()
    }

    suspend fun getOrdersByConsumer(name: String): List<Order> {
        return dbStorage.getOrdersDao().getOrdersByConsumer(name)
    }

    suspend fun getConsumerOrdersSum(name: String): Int {
        return dbStorage.getOrdersDao().getConsumerOrdersSum(name)
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