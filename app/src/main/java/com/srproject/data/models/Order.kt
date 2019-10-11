package com.srproject.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true) var id: Long = -1,
    var consumer: String = "",
    @Ignore var positions: List<OrderPosition> = emptyList(),
    var price: Int = 0,
    var paid: Boolean = false,
    var done: Boolean = false,
    var active: Boolean = false,
    var calculatedPrice: Int = 0,
    var dueDate: Long = 0,
    var comment: String = ""
)

@Entity
data class OrderPosition(
    @PrimaryKey(autoGenerate = true) var id: Long = -1,
    var productId: Long = -1,
    var amount: Int = 0,
    var orderId: Long = -1,
    var comment: String = ""
)

@Entity
data class Product(
    @PrimaryKey var id: Long = -1,
    var name: String = "",
    var price: Int = 0
)