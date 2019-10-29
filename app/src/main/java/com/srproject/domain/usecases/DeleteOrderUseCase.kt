package com.srproject.domain.usecases

import com.srproject.data.Repository

class DeleteOrderUseCase(private val repository: Repository) : BaseUseCase() {

    fun deleteOrder(id: Long) {
        repository.deleteOrder(id)
    }
}