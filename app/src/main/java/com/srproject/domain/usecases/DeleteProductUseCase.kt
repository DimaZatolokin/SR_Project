package com.srproject.domain.usecases

import com.srproject.data.Repository
import kotlinx.coroutines.CoroutineScope

class DeleteProductUseCase(coroutineScope: CoroutineScope, private val repository: Repository) :
    BaseUseCase(coroutineScope) {

    fun deleteProduct(id: Long) {
        repository.deleteProduct(id)
    }
}