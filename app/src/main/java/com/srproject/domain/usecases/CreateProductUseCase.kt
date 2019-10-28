package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Product

class CreateProductUseCase(private val repository: Repository) : BaseUseCase() {

    fun createProduct(product: Product) {
        repository.createProduct(product)
    }
}