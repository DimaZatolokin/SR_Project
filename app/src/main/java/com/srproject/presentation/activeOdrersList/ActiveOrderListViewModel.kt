package com.srproject.presentation.activeOdrersList

import android.app.Application
import androidx.databinding.ObservableBoolean
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetActiveOrdersUseCase
import com.srproject.presentation.BaseViewModel

class ActiveOrderListViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository), ActiveOrderClickListener {

    val adapter = ActiveOrdersAdapter(this)
    private val useCase = GetActiveOrdersUseCase(repository)
    val navigateToDetailsEvent = SingleLiveEvent<Long>()
    val filtersVisible = ObservableBoolean()
    val filterNotDoneSelected = ObservableBoolean()
    val filterNotPaidSelected = ObservableBoolean()
    val noItems = ObservableBoolean(true)
    var isFilterApplied = false

    fun start() {
        useCase.obtainActiveOrders {
            adapter.items = it
            noItems.set(it.isEmpty())
        }
    }

    override fun onCleared() {
        useCase.cancel()
    }

    override fun onOrderClicked(id: Long) {
        navigateToDetailsEvent.postValue(id)
    }

    fun filterClicked() {
        if (noItems.get()) {
            filtersVisible.set(false)
        } else {
            filtersVisible.set(!filtersVisible.get())
        }
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
