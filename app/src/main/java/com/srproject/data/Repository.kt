package com.srproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.srproject.data.models.Order
import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product

class Repository private constructor(
    private val dbStorage: AppDataBase,
    private val preferences: PreferencesDataSource
) {

    suspend fun getActiveOrders(): LiveData<List<Order>> {
        val liveData = MutableLiveData<List<Order>>()
        val positions = arrayListOf<OrderPosition>()
        positions.add(OrderPosition(1, 1, 2, 1, "С молоком"))
        positions.add(OrderPosition(2, 2, 3, 1, "С кофе"))
        positions.add(OrderPosition(3, 3, 1, 1, "без сахара"))
        positions.add(OrderPosition(4, 1, 2, 1, "С молоком"))
        positions.add(OrderPosition(5, 2, 3, 1, "С кофе"))
        positions.add(OrderPosition(6, 3, 1, 1, "без сахара"))
        val list = arrayListOf<Order>()
        list.add(
            Order(
                1,
                "_dddddimma@",
                positions,
                559,
                true,
                false,
                true,
                0,
                System.currentTimeMillis(),
                "The verrrrrrs"
            )
        )
        list.add(
            Order(
                1,
                "Ratyaa",
                positions,
                180,
                true,
                false,
                true,
                180,
                System.currentTimeMillis(),
                "The verrrrrrs"
            )
        )
        list.add(
            Order(
                1,
                "geuss_55",
                positions,
                880,
                true,
                false,
                true,
                920,
                System.currentTimeMillis(),
                "The verrrrrrs"
            )
        )
        list.add(
            Order(
                1,
                "Вася",
                positions,
                290,
                true,
                false,
                true,
                290,
                System.currentTimeMillis(),
                "The verrrrrrs"
            )
        )
        list.add(
            Order(
                1,
                "_dddddimma@",
                positions,
                559,
                true,
                false,
                true,
                0,
                System.currentTimeMillis(),
                "The verrrrrrs"
            )
        )
        list.add(
            Order(
                1,
                "Ratyaa",
                positions,
                180,
                true,
                false,
                true,
                180,
                System.currentTimeMillis(),
                "The verrrrrrs"
            )
        )
        list.add(
            Order(
                1,
                "geuss_55",
                positions,
                880,
                true,
                false,
                true,
                920,
                System.currentTimeMillis(),
                "The verrrrrrs"
            )
        )
        list.add(
            Order(
                1,
                "Вася",
                positions,
                290,
                true,
                false,
                true,
                290,
                System.currentTimeMillis(),
                "The verrrrrrs"
            )
        )
        liveData.postValue(list)
        return liveData
    }

    suspend fun getProductById(id: Long): Product? {
        return Product(id, "Коробка конфет 12", 280)
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