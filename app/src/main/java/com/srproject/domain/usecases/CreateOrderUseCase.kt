package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.domain.mappers.OrderPositionsPresentationMapper
import com.srproject.domain.mappers.OrderPresentationMapper
import com.srproject.presentation.models.OrderUI

class CreateOrderUseCase(private val repository: Repository) : BaseUseCase() {

    private val orderPresentationMapper = OrderPresentationMapper()
    private val positionsPresentationMapper = OrderPositionsPresentationMapper()

    fun createOrder(orderUI: OrderUI) {
        val order = orderPresentationMapper.fromPresentation(orderUI)
        val positions = orderUI.positions.map { positionsPresentationMapper.fromPresentation(it) }
        order.positions = positions
        repository.createOrder(order)
    }

}