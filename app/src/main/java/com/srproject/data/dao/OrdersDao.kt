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
            if (it.amount > 0) {
                insertOrderPosition(it.apply { orderId = idOrder })
            } else {
                it.id?.run {
                    deleteOrderPosition(this)
                }
            }
        }
    }

    @Query("SELECT * FROM `Order` ORDER BY dateCreated")
    abstract fun getAllOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM `Order` WHERE active == 1 ORDER BY dueDate")
    abstract fun getActiveOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM OrderPosition WHERE orderId == :orderId")
    abstract fun getOrderPositions(orderId: Long): List<OrderPosition>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrder(order: Order): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrderPosition(position: OrderPosition)

    @Query("SELECT * FROM `Order` WHERE id == :id")
    abstract fun getOrderById(id: Long): Order?

    @Query("DELETE FROM `Order` WHERE id == :id")
    abstract fun deleteOrder(id: Long)

    @Query("DELETE FROM OrderPosition WHERE id == :id")
    abstract fun deleteOrderPosition(id: Long)

    @Query("SELECT SUM(price) FROM `Order`")
    abstract fun getTotalSoldSum(): Int

    @Query("SELECT SUM(price) FROM `Order` WHERE paid == 1")
    abstract fun getTotalPaidSum(): Int

    @Query("SELECT SUM(price) FROM `Order` WHERE consumer == :name")
    abstract fun getConsumerOrdersSum(name: String): Int

    @Query("SELECT * FROM `Order` WHERE active == 1 AND done == 0 ORDER BY dueDate")
    abstract fun getNotDoneActiveOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM `Order` WHERE active == 1 AND paid == 0 ORDER BY dueDate")
    abstract fun getNotPaidActiveOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM `Order` WHERE active == 1 AND paid == 0 AND done == 0 ORDER BY dueDate")
    abstract fun getNotPaidAndNotDoneActiveOrders(): LiveData<List<Order>>

    @Query("SELECT DISTINCT(consumer) FROM `Order` ORDER BY consumer")
    abstract fun getAllConsumers(): List<String>

    @Query("SELECT * FROM `Order` WHERE consumer == :name ORDER BY dueDate")
    abstract fun getOrdersByConsumer(name: String): List<Order>

    @Query("SELECT SUM(price) FROM `Order` WHERE dateCreated >= :startDate AND dateCreated <= :endDate")
    abstract fun getSoldSumPerPeriod(startDate: Long, endDate: Long): Int

    @Query("SELECT COUNT() FROM `Order` WHERE dateCreated >= :startDate AND dateCreated <= :endDate")
    abstract fun getOrdersAmountPerPeriod(startDate: Long, endDate: Long): Int

    @Query("SELECT COUNT() FROM `Order`")
    abstract fun getTotalOrdersAmount(): Int

    @Query("SELECT COUNT() FROM `Order` WHERE active == 1")
    abstract fun getActiveOrdersAmount(): Int

    @Query("SELECT DISTINCT(consumer) FROM `Order` WHERE consumer LIKE '%' || :nameText || '%' ORDER BY consumer")
    abstract fun findConsumers(nameText: String): List<String>
}