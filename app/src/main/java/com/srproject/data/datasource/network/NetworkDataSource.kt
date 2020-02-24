package com.srproject.data.datasource.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.srproject.data.EmptyError
import com.srproject.data.ResultObject
import com.srproject.data.models.Order
import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product
import kotlinx.coroutines.CompletableDeferred
import java.util.concurrent.atomic.AtomicInteger

private const val KEY_ORDERS = "orders"
private const val KEY_ORDER_POSITIONS = "orderPositions"
private const val KEY_PRODUCTS = "products"

class NetworkDataSource(val firebaseStore: FirebaseFirestore) {

    suspend fun sendOrders(orders: List<Order>): ResultObject<Unit> {
        return sendData(KEY_ORDERS, orders) { it.id.toString() }
    }

    suspend fun sendOrderPositions(orderPositions: List<OrderPosition>): ResultObject<Unit> {
        return sendData(KEY_ORDER_POSITIONS, orderPositions) { it.id.toString() }
    }

    suspend fun sendProducts(products: List<Product>): ResultObject<Unit> {
        return sendData(KEY_PRODUCTS, products) { it.id.toString() }
    }

    private suspend fun <T : Any> sendData(
        remoteKey: String,
        items: List<T>,
        uniqueKeyGenerator: (T) -> String
    ): ResultObject<Unit> {
        val result = CompletableDeferred<ResultObject<Unit>>()
        val successCount = AtomicInteger(items.size)
        synchronized(successCount) {
            items.forEach { item ->
                firebaseStore.collection(remoteKey).document(uniqueKeyGenerator.invoke(item))
                    .set(item)
                    .addOnFailureListener {
                        Log.e(NetworkDataSource::class.java.simpleName, it.message ?: it.toString())
                        result.complete(ResultObject.Error(EmptyError()))
                    }
                    .addOnSuccessListener {
                        successCount.decrementAndGet()
                        Log.i("syncdata", "$remoteKey successCount = $successCount")
                        if (successCount.get() <= 0) {
                            result.complete(ResultObject.Success(Unit))
                        }
                    }
            }
        }
        return result.await()
    }

}