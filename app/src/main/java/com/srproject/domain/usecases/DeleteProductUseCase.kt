package com.srproject.domain.usecases

import com.srproject.data.Repository

class DeleteProductUseCase(private val repository: Repository) : BaseUseCase() {

    fun deleteProduct(id: Long) {
        repository.deleteProduct(id)
    }
}