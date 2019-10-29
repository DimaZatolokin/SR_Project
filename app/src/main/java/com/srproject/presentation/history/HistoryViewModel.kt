package com.srproject.presentation.history

import android.app.Application
import androidx.databinding.ObservableBoolean
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetHistoryUseCase
import com.srproject.presentation.BaseViewModel

class HistoryViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository), OrderClickListener {

    val adapter = OrdersAdapter(this)
    private val useCase = GetHistoryUseCase(repository)
    val noItems = ObservableBoolean(true)
    val navigateToDetailsEvent = SingleLiveEvent<Long>()

    fun start() {
        useCase.obtainAllOrders {
            adapter.items = it
            noItems.set(it.isEmpty())
        }
    }

    override fun onCleared() {
        useCase.cancel()
    }

    override fun onOrderClick(id: Long) {
        navigateToDetailsEvent.postValue(id)
    }
}