package com.srproject.presentation.orderEdit

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.srproject.common.SingleLiveEvent
import com.srproject.common.toReadableDate
import com.srproject.data.Repository
import com.srproject.domain.usecases.UpdateOrderUseCase
import com.srproject.presentation.models.OrderUI
import com.srproject.presentation.orderDetails.OrderDetailsViewModel

class OrderEditViewModel(application: Application, repository: Repository) :
    OrderDetailsViewModel(application, repository) {

    private val updateOrderUseCase = UpdateOrderUseCase(viewModelScope, repository)
    private var id = -1L
    val navigateBackCommand = SingleLiveEvent<Unit>()

    override fun start(id: Long) {
        super.start(id)
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