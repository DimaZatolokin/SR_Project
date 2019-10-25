package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetProductByIdUseCase(coroutineScope: CoroutineScope, private val repository: Repository) :
    BaseUseCase(coroutineScope) {

    fun getProductById(id: Long, action: (Product?) -> Unit) {
        coroutineScope.launch {
            action.invoke(repository.getProductById(id))
        }
    }
}
