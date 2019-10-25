package com.srproject.presentation.orderEdit

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.srproject.common.BaseOrderInfoViewModel
import com.srproject.common.SingleLiveEvent
import com.srproject.common.toReadableDate
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetOrderDetailsUseCase
import com.srproject.domain.usecases.GetProductsUseCase
import com.srproject.domain.usecases.UpdateOrderUseCase
import com.srproject.presentation.createOrder.OrderCreatePositionsAdapter
import com.srproject.presentation.createOrder.UpdatePriceListener
import com.srproject.presentation.models.OrderPositionUI
import com.srproject.presentation.models.OrderUI

class OrderEditViewModel(application: Application, repository: Repository) :
    BaseOrderInfoViewModel(application, repository), UpdatePriceListener {

    private val getOrderUseCase = GetOrderDetailsUseCase(viewModelScope, repository)
    val adapter = OrderCreatePositionsAdapter(this)
    private val updateOrderUseCase = UpdateOrderUseCase(viewModelScope, repository)
    private val getProductsUseCase = GetProductsUseCase(viewModelScope, repository)
    private var id = -1L
    val navigateBackCommand = SingleLiveEvent<Unit>()
    val isAddPositionButtonEnabled = ObservableBoolean()
    val showErrorCommand = SingleLiveEvent<Errors>()
    val showExitDialogCommand = SingleLiveEvent<Unit>()

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
                adapter.items = this.positions as ArrayList<OrderPositionUI>
            }
        }
        getProductsUseCase.obtainProducts { products ->
            adapter.products = products
            isAddPositionButtonEnabled.set(products.isNotEmpty())
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
        if (consumer.get().isNullOrBlank()) {
            showErrorCommand.postValue(Errors.CONSUMER)
            return false
        }

        if (adapter.items.none { it.amount > 0 }) {
            showErrorCommand.postValue(Errors.POSITIONS)
            return false
        }
        return true
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
        adapter.createNewItem()
    }

    override fun onUpdatePrice() {
        calculatedPrice.set(adapter.getTotalPrice().toString())
    }

    fun backPressed() {
        if (adapter.products.isNotEmpty()) {
            showExitDialogCommand.call()
        } else {
            navigateBackCommand.call()
        }
    }

    override fun onCleared() {
        super.onCleared()
        getProductsUseCase.onClear()
        getOrderUseCase.onClear()
        updateOrderUseCase.onClear()
    }
}

enum class Errors { CONSUMER, POSITIONS }