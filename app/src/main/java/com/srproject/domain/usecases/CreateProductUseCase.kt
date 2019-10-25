package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Product
import kotlinx.coroutines.CoroutineScope

class CreateProductUseCase(coroutineScope: CoroutineScope, private val repository: Repository) :
    BaseUseCase(coroutineScope) {

    fun createProduct(product: Product) {
        repository.createProduct(product)
    }
}