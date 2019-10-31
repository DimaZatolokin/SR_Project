package com.srproject.presentation.activeOdrersList

import android.app.Application
import androidx.databinding.ObservableBoolean
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.domain.usecases.FilterOrdersUseCase
import com.srproject.domain.usecases.GetActiveOrdersUseCase
import com.srproject.presentation.BaseViewModel

class ActiveOrderListViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository), ActiveOrderClickListener {

    val adapter = ActiveOrdersAdapter(this)
    private val useCase = GetActiveOrdersUseCase(repository)
    private val filterOrdersUseCase = FilterOrdersUseCase(repository)
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

    private fun filterItems() {
        filterOrdersUseCase.filterOrders(filterNotDoneSelected.get(), filterNotPaidSelected.get()) {
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
        filtersVisible.set(!filtersVisible.get())
    }

    fun onFilterDoneClicked() {
        filterNotDoneSelected.set(!filterNotDoneSelected.get())
        isFilterApplied = filterNotPaidSelected.get() || filterNotDoneSelected.get()
        filterItems()
    }

    fun onFilterPaidClicked() {
        filterNotPaidSelected.set(!filterNotPaidSelected.get())
        isFilterApplied = filterNotPaidSelected.get() || filterNotDoneSelected.get()
        filterItems()
    }
}
