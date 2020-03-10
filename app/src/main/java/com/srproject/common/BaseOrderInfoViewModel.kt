package com.srproject.common

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetConsumersUseCase
import com.srproject.presentation.BaseViewModel
import com.srproject.presentation.findConsumers.FindConsumersAdapter
import com.srproject.presentation.findConsumers.OnConsumerClickListener

abstract class BaseOrderInfoViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    private val getConsumersUseCase = GetConsumersUseCase(repository)

    val consumer = ObservableField<String>()
    val dueDate = ObservableField<String>()
    val dateCreated = ObservableField<String>()
    val comment = ObservableField<String>()
    val realPrice = ObservableField<String>()
    val calculatedPrice = ObservableField<String>()
    val done = ObservableBoolean()
    val active = ObservableBoolean()
    val paid = ObservableBoolean()
    val number = ObservableLong()
    val consumersAdapter = FindConsumersAdapter(object : OnConsumerClickListener {
        override fun onItemClicked(name: String) {
            consumer.set(name)
            isUserInputtingSomething.set(false)
            consumerSelectedCommand.call()
        }
    })
    private var customerFocused = false
    private val isUserInputtingSomething = ObservableBoolean()
    val showConsumersList = object : ObservableBoolean(consumer, isUserInputtingSomething) {
        override fun get(): Boolean {
            return !consumer.get().isNullOrBlank() && isUserInputtingSomething.get() && customerFocused
        }
    }
    val consumerSelectedCommand = SingleLiveEvent<Unit>()

    fun findConsumers() {
        isUserInputtingSomething.set(true)
        getConsumersUseCase.findConsumers(consumer.get().toString()) {
            consumersAdapter.items = it
        }
    }

    fun somethingInputting(isInputting: Boolean) {
        isUserInputtingSomething.set(isInputting)
    }

    fun setCustomerFocused(hasFocus: Boolean) {
        customerFocused = hasFocus
    }
}