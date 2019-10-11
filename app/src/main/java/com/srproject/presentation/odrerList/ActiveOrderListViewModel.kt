package com.srproject.presentation.odrerList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.srproject.data.models.Order
import com.srproject.data.Repository
import com.srproject.presentation.BaseViewModel
import com.srproject.presentation.models.OrderPositionUI
import com.srproject.presentation.models.OrderUI
import com.srproject.presentation.odrerList.mappers.OrderPositionsPresentationMapper
import com.srproject.presentation.odrerList.mappers.OrderPresentationMapper
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ActiveOrderListViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    val adapter = OrdersAdapter()
    private val orderPresentationMapper = OrderPresentationMapper()
    private val positionPresentationMapper = OrderPositionsPresentationMapper()
    private lateinit var activeOrdersLiveData: LiveData<List<Order>>
    private val activeOrdersObserver = Observer<List<Order>> { list ->
        viewModelScope.launch {
            val orderUIs = arrayListOf<OrderUI>()
            list.forEach { order ->
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
            adapter.items = orderUIs
        }
    }

    init {
        viewModelScope.launch {
            activeOrdersLiveData = repository.getActiveOrders(viewModelScope)
            activeOrdersLiveData.observeForever(activeOrdersObserver)
        }
    }

    override fun onCleared() {
        if (::activeOrdersLiveData.isInitialized) {
            activeOrdersLiveData.removeObserver(activeOrdersObserver)
        }
    }
}