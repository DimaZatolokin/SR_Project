package com.srproject.domain.usecases

import com.srproject.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetConsumersUseCase(private val repository: Repository) : BaseUseCase() {

    fun findConsumers(action: (List<String>) -> Unit) {
        launch {
            val consumers = repository.getConsumers()
            withContext(Dispatchers.Main) {
                action.invoke(consumers)
            }
        }
    }

    fun findConsumers(nameText: String, action: (List<String>) -> Unit) {
        launch {
            val consumers = repository.findConsumers(nameText)
            withContext(Dispatchers.Main) {
                action.invoke(consumers)
            }
        }
    }
}