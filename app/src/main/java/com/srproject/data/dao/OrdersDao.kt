package com.srproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.srproject.data.models.Order
import com.srproject.data.models.OrderPosition

@Dao
abstract class OrdersDao {

    @Transaction
    open fun saveOrder(order: Order) {
        val idOrder = insertOrder(order)
        order.positions.forEach {
            insertOrderPosition(it.apply { orderId = idOrder })
        }
    }

    @Query("SELECT * FROM `Order`")
    abstract fun getAllOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM `Order` WHERE active == 'true'")
    abstract fun getActiveOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM OrderPosition WHERE orderId == :orderId")
    abstract fun getOrderPositions(orderId: Long): List<OrderPosition>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrder(order: Order): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrderPosition(position: OrderPosition)

    @Query("SELECT * FROM `Order` WHERE id == :id")
    abstract fun getOrderById(id: Long): Order?
}