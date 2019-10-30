package com.srproject.presentation.statistics

import android.app.Application
import androidx.databinding.ObservableInt
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetTotalSentSumUseCase
import com.srproject.presentation.BaseViewModel

class StatisticsViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    private val getTotalSentSumUseCase = GetTotalSentSumUseCase(repository)
    val totalSent = ObservableInt()

    fun start() {
        getTotalSentSumUseCase.getTotalSentSum {
            totalSent.set(it)
        }
    }
}