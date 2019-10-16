package com.srproject.domain.mappers

import com.srproject.common.toReadableDate
import com.srproject.data.models.Order
import com.srproject.presentation.models.OrderUI

class OrderPresentationMapper : PresentationMapper<Order, OrderUI> {

    override fun toPresentation(model: Order) = OrderUI(
        model.id,
        model.consumer,
        model.price,
        model.paid,
        model.done,
        model.active,
        model.calculatedPrice,
        model.dateCreated.toReadableDate(),
        model.dueDate.toReadableDate(),
        model.comment,
        emptyList()
    )
}