package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.presentation.models.OrderUI
import kotlinx.coroutines.CoroutineScope

class CreateOrderUseCase(coroutineScope: CoroutineScope, repository: Repository) :
    BaseUseCase(coroutineScope) {

    fun createOrder(orderUI: OrderUI) {
        //todo
    }

}