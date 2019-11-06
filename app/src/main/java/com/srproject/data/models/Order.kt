package com.srproject.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var consumer: String = "",
    @Ignore var positions: List<OrderPosition> = emptyList(),
    var price: Int = 0,
    var paid: Boolean = false,
    var done: Boolean = false,
    var active: Boolean = false,
    var calculatedPrice: Int = 0,
    var dateCreated: Long = 0,
    var dueDate: Long = 0,
    var comment: String = ""
)


@Entity(
    foreignKeys = [ForeignKey(
        entity = Order::class,
        parentColumns = ["id"],
        childColumns = ["orderId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class OrderPosition(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var productId: Long = -1,
    var amount: Int = 0,
    var done: Boolean,
    var orderId: Long? = null
)

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var name: String = "",
    var price: Int = 0
)