package com.srproject.presentation.odrerList.mappers

import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product
import com.srproject.presentation.common.PresentationMapper
import com.srproject.presentation.models.OrderPositionUI

class OrderPositionsPresentationMapper : PresentationMapper<OrderPosition, OrderPositionUI> {

    override fun toPresentation(model: OrderPosition) = OrderPositionUI(
        model.id,
        Product(model.productId),
        model.amount,
        model.comment
    )
}