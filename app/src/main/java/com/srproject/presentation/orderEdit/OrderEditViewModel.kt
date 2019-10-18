package com.srproject.presentation.orderEdit

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.srproject.common.BaseOrderInfoViewModel
import com.srproject.common.SingleLiveEvent
import com.srproject.common.toReadableDate
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetOrderDetailsUseCase
import com.srproject.domain.usecases.UpdateOrderUseCase
import com.srproject.presentation.models.OrderUI
import com.srproject.presentation.orderDetails.OrderDetailsPositionsAdapter

class OrderEditViewModel(application: Application, repository: Repository) :
    BaseOrderInfoViewModel(application, repository) {

    private val getOrderUseCase = GetOrderDetailsUseCase(viewModelScope, repository)
    val adapter = OrderDetailsPositionsAdapter() //TODO change on own adapter
    private val updateOrderUseCase = UpdateOrderUseCase(viewModelScope, repository)
    private var id = -1L
    val navigateBackCommand = SingleLiveEvent<Unit>()

    fun start(id: Long) {
        getOrderUseCase.obtainOrderDetails(id) {
            it?.run {
                this@OrderEditViewModel.consumer.set(consumer)
                this@OrderEditViewModel.dueDate.set(dueDate)
                this@OrderEditViewModel.dateCreated.set(dateCreated)
                this@OrderEditViewModel.realPrice.set(price.toString())
                this@OrderEditViewModel.calculatedPrice.set(calculatedPrice.toString())
                this@OrderEditViewModel.done.set(done)
                this@OrderEditViewModel.active.set(active)
                this@OrderEditViewModel.paid.set(paid)
                this@OrderEditViewModel.comment.set(comment)
                adapter.items = this.positions
            }
        }
        this.id = id
    }

    fun onSaveClicked() {
        if (areFieldsValid()) {
            val orderUI = OrderUI(
                id,
                consumer.get()!!,
                if (realPrice.get().isNullOrEmpty()) 0 else realPrice.get()!!.toInt(),
                paid.get(),
                done.get(),
                active.get(),
                calculatedPrice.get()!!.toInt(),
                dateCreated.get()!!,
                dueDate.get()!!,
                comment.get() ?: "",
                adapter.items
            )
            updateOrderUseCase.updateOrder(orderUI)
            navigateBackCommand.call()
        }
    }

    private fun areFieldsValid(): Boolean {
        return !consumer.get().isNullOrBlank()
    }

    fun setDateCreated(timeInMillis: Long) {
        dateCreated.set(timeInMillis.toReadableDate())
    }

    fun setDueDate(timeInMillis: Long) {
        dueDate.set(timeInMillis.toReadableDate())
    }

    fun onPaidClicked() {
        paid.set(!paid.get())
    }

    fun onDoneClicked() {
        done.set(!done.get())
    }

    fun onGivenClicked() {
        active.set(!active.get())
    }

    fun onAddPositionClicked() {
        //TODO
    }
}