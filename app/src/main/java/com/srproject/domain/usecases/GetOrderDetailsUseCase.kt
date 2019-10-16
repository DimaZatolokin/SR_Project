package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Product
import com.srproject.domain.mappers.OrderPositionsPresentationMapper
import com.srproject.domain.mappers.OrderPresentationMapper
import com.srproject.presentation.models.OrderUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetOrderDetailsUseCase(coroutineScope: CoroutineScope, private val repository: Repository) :
    BaseUseCase(coroutineScope) {

    private val orderPresentationMapper = OrderPresentationMapper()
    private val positionPresentationMapper = OrderPositionsPresentationMapper()

    fun obtainOrderDetails(id: Long, action: (OrderUI?) -> Unit) {
        coroutineScope.launch {
            val order = repository.getOrderById(id)
            order?.run {
                val orderUI = orderPresentationMapper.toPresentation(order)
                val positions = repository.getOrderPositionsByOrderId(id)
                orderUI.positions = positions.map {
                    positionPresentationMapper.toPresentation(it).apply {
                        val product = repository.getProductById(it.productId)
                        this.product = product ?: Product()
                    }
                }
                action.invoke(orderUI)
            } ?: action.invoke(null)
        }
    }
}
