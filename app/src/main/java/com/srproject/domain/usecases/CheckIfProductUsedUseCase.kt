package com.srproject.domain.usecases

import com.srproject.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckIfProductUsedUseCase(private val repository: Repository) : BaseUseCase() {

    fun checkIfProductUsed(id: Long, action: (Boolean) -> Unit) {
        launch {
            val count = repository.getProductUsagesCount(id)
            withContext(Dispatchers.Main) { action.invoke(count > 0) }
        }
    }
}