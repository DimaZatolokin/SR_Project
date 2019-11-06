package com.srproject.presentation.models

import com.srproject.data.models.Product

data class OrderUI(
    val id: Long?,
    val consumer: String,
    val price: Int,
    val paid: Boolean,
    val done: Boolean,
    val active: Boolean,
    var calculatedPrice: Int,
    val dateCreated: String,
    val dueDate: String,
    val comment: String,
    var positions: List<OrderPositionUI>
)

data class OrderPositionUI(
    val id: Long?,
    var product: Product,
    var amount: Int,
    var done: Boolean,
    val orderId: Long?
)