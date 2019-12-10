package com.srproject.presentation.statistics

import android.app.Application
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.srproject.common.nextMonth
import com.srproject.common.previousMonth
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetSoldSumUseCase
import com.srproject.presentation.BaseViewModel
import java.util.*

class StatisticsViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    private val getSoldSumUseCase = GetSoldSumUseCase(repository)
    val totalSold = ObservableInt()
    val totalPaid = ObservableInt()
    val date = ObservableField<Date>()
    val monthlySold = ObservableInt()

    fun start() {
        getSoldSumUseCase.apply {
            getTotalSoldSum {
                totalSold.set(it)
            }
            getTotalPaidSum {
                totalPaid.set(it)
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
            getSoldSumUseCase.getSoldSumPerMonth(this) {
                monthlySold.set(it)
            }
        }
    }
}