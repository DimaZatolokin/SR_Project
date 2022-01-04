package com.srproject.data.models

data class FileDataModel(
    val orders: List<Order>,
    val positions: List<OrderPosition>,
    val products: List<Product>
)
