package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Product
import kotlinx.coroutines.CoroutineScope

class UpdateProductUseCase (coroutineScope: CoroutineScope, private val repository: Repository) :
    BaseUseCase(coroutineScope) {

    fun updateProduct(product: Product) {
        repository.updateProduct(product)
    }
}