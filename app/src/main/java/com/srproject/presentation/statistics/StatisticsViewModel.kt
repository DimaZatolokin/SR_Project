package com.srproject.presentation.statistics

import android.app.Application
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.srproject.common.nextMonth
import com.srproject.common.previousMonth
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetStatisticsUseCase
import com.srproject.presentation.BaseViewModel
import java.util.*

class StatisticsViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    private val getStatisticsUseCase = GetStatisticsUseCase(repository)
    val totalSold = ObservableInt()
    val totalPaid = ObservableInt()
    val date = ObservableField<Date>()
    val monthlySold = ObservableInt()
    val monthlyOrders = ObservableInt()
    val totalOrdersAmount = ObservableInt()
    val activeOrdersAmount = ObservableInt()

    fun start() {
        getStatisticsUseCase.apply {
            getTotalSoldSum {
                totalSold.set(it)
            }
            getTotalPaidSum {
                totalPaid.set(it)
            }
            getOrdersAmount {
                totalOrdersAmount.set(it)
            }
            getActiveOrdersAmount {
                activeOrdersAmount.set(it)
            }
        }
        date.set(Date(System.currentTimeMillis()))
        setCurrentMonthlySold()
    }

    fun onPreviousMonthClicked() {
        date.set(date.get()?.previousMonth())
        setCurrentMonthlySold()
    }

    fun onNextMonthClicked() {
        date.set(date.get()?.nextMonth())
        setCurrentMonthlySold()
    }

    private fun setCurrentMonthlySold() {
        date.get()?.run {
            getStatisticsUseCase.let { useCase ->
                useCase.getSoldSumPerMonth(this) {
                    monthlySold.set(it)
                }
                useCase.getOrdersAmountBerMonth(this) {
                    monthlyOrders.set(it)
                }
            }
        }
    }
}