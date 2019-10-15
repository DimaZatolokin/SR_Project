package com.srproject.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.srproject.data.Repository
import com.srproject.data.models.Order
import com.srproject.presentation.models.OrderPositionUI
import com.srproject.presentation.models.OrderUI
import com.srproject.domain.mappers.OrderPositionsPresentationMapper
import com.srproject.domain.mappers.OrderPresentationMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetActiveOrdersUseCase(coroutineScope: CoroutineScope, private val repository: Repository) :
    BaseUseCase(coroutineScope) {

    private val orderPresentationMapper = OrderPresentationMapper()
    private val positionPresentationMapper = OrderPositionsPresentationMapper()
    private lateinit var activeOrdersLiveData: LiveData<List<Order>>
    private val activeOrdersObserver = Observer<List<Order>> { list ->
        val orderUIs = arrayListOf<OrderUI>()
        list.forEach { order ->
            coroutineScope.launch {
                val positionsUIs = arrayListOf<OrderPositionUI>()
                order.positions.forEach { orderPosition ->
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
            action?.invoke(orderUIs)
        }
    }
    private var action: ((List<OrderUI>) -> Unit)? = null

    override fun onClear() {
        if (::activeOrdersLiveData.isInitialized) {
            activeOrdersLiveData.removeObserver(activeOrdersObserver)
        }
    }

    fun obtainActiveOrders(action: (List<OrderUI>) -> Unit) {
        this.action = action
        coroutineScope.launch {
            activeOrdersLiveData = repository.getActiveOrders()
            activeOrdersLiveData.observeForever(activeOrdersObserver)
        }
    }
}