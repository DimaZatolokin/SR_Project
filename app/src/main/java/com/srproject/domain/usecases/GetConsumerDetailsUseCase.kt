package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Order
import com.srproject.domain.mappers.OrderPositionsPresentationMapper
import com.srproject.domain.mappers.OrderPresentationMapper
import com.srproject.presentation.models.OrderPositionUI
import com.srproject.presentation.models.OrderUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetConsumerDetailsUseCase(private val repository: Repository) : BaseUseCase() {

    private val orderPresentationMapper = OrderPresentationMapper()
    private val positionPresentationMapper = OrderPositionsPresentationMapper()

    fun obtainConsumerOrdersSum(name: String, action: (Int) -> Unit) {
        launch {
            val sum = repository.getConsumerOrdersSum(name)
            withContext(Dispatchers.Main) {
                action.invoke(sum)
            }
        }
    }

    fun obtainConsumerOrders(name: String, action: (List<OrderUI>) -> Unit) {
        launch {
            val orders = convertOrderListToPresentation(repository.getOrdersByConsumer(name))
            withContext(Dispatchers.Main) {
                action.invoke(orders)
            }
        }
    }

    private suspend fun convertOrderListToPresentation(orders: List<Order>): List<OrderUI> {
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
        return orderUIs
    }
}