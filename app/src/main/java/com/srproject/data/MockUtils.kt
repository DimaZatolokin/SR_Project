package com.srproject.data

import com.srproject.data.models.Order
import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product

fun mockPositions(): List<OrderPosition> {
    val positions = arrayListOf<OrderPosition>()
    positions.add(OrderPosition(1, 1, 2, 1))
    positions.add(OrderPosition(2, 2, 3, 1))
    positions.add(OrderPosition(3, 3, 1, 1))
    positions.add(OrderPosition(4, 1, 2, 1))
    positions.add(OrderPosition(5, 2, 3, 1))
    positions.add(OrderPosition(6, 3, 1, 1))
    return positions
}

fun mockProducts(): List<Product> {
    val products = arrayListOf<Product>()
    products.add(Product(1, "Коробка конфет 12", 240))
    products.add(Product(2, "Коробка конфет 16", 290))
    products.add(Product(3, "Коробка шоколадок c малиновой начинкой", 300))
    products.add(Product(4, "Шоколадка белая", 130))
    products.add(Product(5, "Шоколадка черная", 130))
    products.add(Product(6, "Шоколадка молочная", 140))
    products.add(Product(7, "Шоколадка руби", 150))
    return products
}

fun mockOrder(): Order {
    return Order(
        15,
        "Vasya Pupkin",
        emptyList(),
        255,
        true,
        false,
        true,
        245,
        System.currentTimeMillis() - 100000000,
        System.currentTimeMillis(),
        "без сахара"
    )
}

fun mockActiveOrders(): List<Order> {
    val list = arrayListOf<Order>()
    list.add(
        Order(
            1,
            "_dddddimma@",
            mockPositions(),
            559,
            true,
            true,
            true,
            0,
            System.currentTimeMillis() - 100000000,
            System.currentTimeMillis(),
            "The verrrrrrs"
        )
    )
    list.add(
        Order(
            1,
            "Ratyaa",
            mockPositions(),
            180,
            true,
            true,
            false,
            180,
            System.currentTimeMillis() - 100000000,
            System.currentTimeMillis(),
            "The verrrrrrs"
        )
    )
    list.add(
        Order(
            1,
            "geuss_55",
            mockPositions(),
            880,
            false,
            false,
            true,
            920,
            System.currentTimeMillis() - 100000000,
            System.currentTimeMillis(),
            "The verrrrrrs"
        )
    )
    list.add(
        Order(
            1,
            "Вася",
            mockPositions(),
            290,
            true,
            false,
            true,
            290,
            System.currentTimeMillis() - 100000000,
            System.currentTimeMillis(),
            "The verrrrrrs"
        )
    )
    list.add(
        Order(
            1,
            "_dddddimma@",
            mockPositions(),
            559,
            true,
            false,
            true,
            0,
            System.currentTimeMillis() - 100000000,
            System.currentTimeMillis(),
            "The verrrrrrs"
        )
    )
    list.add(
        Order(
            1,
            "Ratyaa",
            mockPositions(),
            180,
            true,
            false,
            true,
            180,
            System.currentTimeMillis() - 100000000,
            System.currentTimeMillis(),
            "The verrrrrrs"
        )
    )
    list.add(
        Order(
            1,
            "geuss_55",
            mockPositions(),
            880,
            true,
            false,
            true,
            920,
            System.currentTimeMillis() - 100000000,
            System.currentTimeMillis(),
            "The verrrrrrs"
        )
    )
    list.add(
        Order(
            1,
            "Вася",
            mockPositions(),
            290,
            true,
            false,
            true,
            290,
            System.currentTimeMillis() - 100000000,
            System.currentTimeMillis(),
            "The verrrrrrs"
        )
    )
    return list
}