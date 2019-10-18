package com.srproject.presentation.createOrder

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.srproject.common.BaseOrderInfoViewModel
import com.srproject.common.SingleLiveEvent
import com.srproject.common.toReadableDate
import com.srproject.data.Repository
import com.srproject.data.models.Product
import com.srproject.domain.usecases.GetProductsUseCase
import com.srproject.presentation.models.OrderPositionUI
import com.srproject.presentation.models.OrderUI

class OrderCreateViewModel(application: Application, repository: Repository) :
    BaseOrderInfoViewModel(application, repository) {

    val adapter = OrderCreatePositionsAdapter()
    val showErrorCommand = SingleLiveEvent<Errors>()
    private val getProductsUseCase = GetProductsUseCase(viewModelScope, repository)

    init {
        dateCreated.set(System.currentTimeMillis().toReadableDate())
        active.set(true)
        calculatedPrice.set(0.toString())
        getProductsUseCase.obtainProducts { products ->
            adapter.products = products.map { it.name }
        }
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
        adapter.addItem(OrderPositionUI(1, Product(1, "UUUUU", 500), 32, "fff", 1))
    }

    fun onSaveClicked() {
        if (areFieldsValid()) {
            val orderUI = OrderUI(
                -1,
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
//            updateOrderUseCase.updateOrder(orderUI)
//            navigateBackCommand.call()
        }
    }

    private fun areFieldsValid(): Boolean {
        if (consumer.get().isNullOrBlank()) {
            showErrorCommand.postValue(Errors.CONSUMER)
            return false
        }
        if (dueDate.get().isNullOrBlank()) {
            showErrorCommand.postValue(Errors.DUE_DATE)
            return false
        }
        if (adapter.items.isEmpty()) {
            showErrorCommand.postValue(Errors.POSITIONS)
            return false
        }
        return true
    }

}

enum class Errors { CONSUMER, DUE_DATE, POSITIONS }