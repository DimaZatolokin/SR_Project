package com.srproject.presentation.orderDetails

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetOrderDetailsUseCase
import com.srproject.presentation.BaseViewModel

class OrderDetailsViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    private val useCase = GetOrderDetailsUseCase(viewModelScope, repository)
    val adapter = OrderDetailsPositionsAdapter()

    val consumer = ObservableField<String>()
    val dueDate = ObservableField<String>()
    val dateCreated = ObservableField<String>()
    val comment = ObservableField<String>()
    val realPrice = ObservableField<Int>()
    val calculatedPrice = ObservableField<Int>()
    val done = ObservableBoolean()
    val active = ObservableBoolean()
    val paid = ObservableBoolean()

    fun start(id: Long) {
        useCase.obtainOrderDetails(id) {
            it?.run {
                this@OrderDetailsViewModel.consumer.set(consumer)
                this@OrderDetailsViewModel.dueDate.set(dueDate)
                this@OrderDetailsViewModel.dateCreated.set(dateCreated)
                this@OrderDetailsViewModel.realPrice.set(price)
                this@OrderDetailsViewModel.calculatedPrice.set(calculatedPrice)
                this@OrderDetailsViewModel.done.set(done)
                this@OrderDetailsViewModel.active.set(active)
                this@OrderDetailsViewModel.paid.set(paid)
                this@OrderDetailsViewModel.comment.set(comment)
                adapter.items = this.positions
            }
        }
    }

    override fun onCleared() {
        useCase.onClear()
    }
}