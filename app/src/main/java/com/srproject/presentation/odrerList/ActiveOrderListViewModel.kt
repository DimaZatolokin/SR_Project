package com.srproject.presentation.odrerList

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetActiveOrdersUseCase
import com.srproject.presentation.BaseViewModel

class ActiveOrderListViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    val adapter = OrdersAdapter()
    private val useCase = GetActiveOrdersUseCase(viewModelScope, repository)

    init {
        useCase.obtainActiveOrders {
            adapter.items = it
        }
    }

    override fun onCleared() {
        useCase.onClear()
    }
}
