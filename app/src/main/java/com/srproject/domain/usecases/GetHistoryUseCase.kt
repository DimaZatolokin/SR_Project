package com.srproject.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.srproject.data.Repository
import com.srproject.data.models.Order
import com.srproject.domain.mappers.OrderPositionsPresentationMapper
import com.srproject.domain.mappers.OrderPresentationMapper
import com.srproject.presentation.models.OrderPositionUI
import com.srproject.presentation.models.OrderUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetHistoryUseCase(private val repository: Repository) : BaseUseCase() {

    private val orderPresentationMapper = OrderPresentationMapper()
    private val positionPresentationMapper = OrderPositionsPresentationMapper()
    private lateinit var ordersLiveData: LiveData<List<Order>>
    private val ordersObserver = Observer<List<Order>> { orders ->
        launch {
            val orderUIs = arrayListOf<OrderUI>()
            orders.forEach { order ->
                val positions = repository.getOrderPositionsByOrderId(order.id!!)
                val positionsUIs = arrayListOf<OrderPositionUI>()
                positions.forEach { orderPosition ->
                    positionsUIs.add(positionPresentationMapper.toPresentation(orderPosition).apply {
                        repository.getProductById(orderPosition.productId)?.let {
                            this.product = it
                        }
                    })
                }
                orderUIs.add(orderPresentationMapper.toPresentation(order).apply {
                    this.positions = positionsUIs
                    this.calculatedPrice = positionsUIs.let { positions ->
                        var sum = 0
                        positions.forEach { sum += it.product.price * it.amount }
                        sum
                    }
                })
            }
            withContext(Dispatchers.Main) { action?.invoke(orderUIs) }
        }
    }
    private var action: ((List<OrderUI>) -> Unit)? = null

    override fun cancel() {
        super.cancel()
        if (::ordersLiveData.isInitialized) {
            ordersLiveData.removeObserver(ordersObserver)
        }
    }

    fun obtainAllOrders(action: (List<OrderUI>) -> Unit) {
        this.action = action
        launch {
            ordersLiveData = repository.getAllOrders()
            withContext(Dispatchers.Main) { ordersLiveData.observeForever(ordersObserver) }
        }
    }
}