package com.srproject.presentation.consumerDetails

import android.app.Application
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetConsumerDetailsUseCase
import com.srproject.presentation.BaseViewModel
import com.srproject.presentation.history.OrdersAdapter

class ConsumerDetailsViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    val name = ObservableField<String>()
    val sum = ObservableInt()
    private val useCase = GetConsumerDetailsUseCase(repository)
    val adapter = OrdersAdapter(null)

    fun start(name: String) {
        this.name.set(name)
        useCase.obtainConsumerOrdersSum(name) {
            sum.set(it)
        }
        useCase.obtainConsumerOrders(name) {
            adapter.items = it
        }
    }
}