package com.srproject.domain.usecases

import com.srproject.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetTotalSentSumUseCase(private val repository: Repository) : BaseUseCase() {

    fun getTotalSentSum(action: (Int) -> Unit) {
        launch {
            val sum = repository.getTotalSoldSum()
            withContext(Dispatchers.Main) {
                action.invoke(sum)
            }
        }
    }
}