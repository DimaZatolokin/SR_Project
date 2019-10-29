package com.srproject.domain.usecases

import com.srproject.data.Repository
import kotlinx.coroutines.launch

class CheckIfProductUsedUseCase(private val repository: Repository) : BaseUseCase() {

    fun checkIfProductUsed(id: Long, action: (Boolean) -> Unit) {
        launch {
            action.invoke(repository.getProductUsagesCount(id) > 0)
        }
    }
}