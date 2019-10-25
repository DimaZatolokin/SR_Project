package com.srproject.presentation.activeOdrersList

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetActiveOrdersUseCase
import com.srproject.presentation.BaseViewModel

class ActiveOrderListViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository), ActiveOrderClickListener {

    val adapter = OrdersAdapter(this)
    private val useCase = GetActiveOrdersUseCase(viewModelScope, repository)
    val navigateToDetailsEvent = SingleLiveEvent<Long>()
    val filtersVisible = ObservableBoolean()
    val filterNotDoneSelected = ObservableBoolean()
    val filterNotPaidSelected = ObservableBoolean()
    var isFilterApplied = false

    fun start() {
        useCase.obtainActiveOrders {
            adapter.items = it
        }
    }

    override fun onCleared() {
        useCase.onClear()
    }

    override fun onOrderClicked(id: Long) {
        navigateToDetailsEvent.postValue(id)
    }

    fun filterClicked() {
        filtersVisible.set(!filtersVisible.get())
    }

    fun onFilterDoneClicked() {
        filterNotDoneSelected.set(!filterNotDoneSelected.get())
        isFilterApplied = filterNotPaidSelected.get() || filterNotDoneSelected.get()
    }

    fun onFilterPaidClicked() {
        filterNotPaidSelected.set(!filterNotPaidSelected.get())
        isFilterApplied = filterNotPaidSelected.get() || filterNotDoneSelected.get()
    }
}
