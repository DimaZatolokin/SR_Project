package com.srproject.domain.usecases

import com.srproject.common.getBeginMonthDate
import com.srproject.common.getEndMonthDate
import com.srproject.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class GetStatisticsUseCase(private val repository: Repository) : BaseUseCase() {

    fun getTotalSoldSum(action: (Int) -> Unit) {
        launch {
            val sum = repository.getTotalSoldSum()
            withContext(Dispatchers.Main) {
                action.invoke(sum)
            }
        }
    }

    fun getTotalPaidSum(action: (Int) -> Unit) {
        launch {
            val sum = repository.getTotalPaidSum()
            withContext(Dispatchers.Main) {
                action.invoke(sum)
            }
        }
    }

    fun getSoldSumPerMonth(date: Date, action: (Int) -> Unit) {
        launch {
            val sum =
                repository.getSoldSumPerPeriod(date.getBeginMonthDate(), date.getEndMonthDate())
            withContext(Dispatchers.Main) {
                action.invoke(sum)
            }
        }
    }

    fun getOrdersAmountBerMonth(date: Date, action: (Int) -> Unit) {
        launch {
            val sum = repository.getOrdersAmountPerPeriod(
                date.getBeginMonthDate(),
                date.getEndMonthDate()
            )
            withContext(Dispatchers.Main) {
                action.invoke(sum)
            }
        }
    }

    fun getOrdersAmount(action: (Int) -> Unit) {
        launch {
            val sum = repository.getOrdersAmount()
            withContext(Dispatchers.Main) {
                action.invoke(sum)
            }
        }
    }

    fun getActiveOrdersAmount(action: (Int) -> Unit) {
        launch {
            val sum = repository.getActiveOrdersAmount()
            withContext(Dispatchers.Main) {
                action.invoke(sum)
            }
        }
    }
}