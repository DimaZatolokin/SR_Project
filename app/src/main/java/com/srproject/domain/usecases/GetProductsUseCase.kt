package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetProductsUseCase(coroutineScope: CoroutineScope, private val repository: Repository) :
    BaseUseCase(coroutineScope) {

    fun obtainProducts(action: (List<Product>) -> Unit) {
        coroutineScope.launch {
            val products = repository.getProducts()
            action.invoke(products)
        }
    }
}