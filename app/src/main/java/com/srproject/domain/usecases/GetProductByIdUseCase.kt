package com.srproject.domain.usecases

import com.srproject.data.Repository
import com.srproject.data.models.Product
import kotlinx.coroutines.launch

class GetProductByIdUseCase(private val repository: Repository) : BaseUseCase() {

    fun getProductById(id: Long, action: (Product?) -> Unit) {
        launch {
            action.invoke(repository.getProductById(id))
        }
    }
}
