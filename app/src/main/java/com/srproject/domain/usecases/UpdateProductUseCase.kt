package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Product

class UpdateProductUseCase(private val repository: Repository) : BaseUseCase() {

    fun updateProduct(product: Product) {
        repository.updateProduct(product)
    }
}