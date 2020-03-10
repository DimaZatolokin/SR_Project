package com.srproject.presentation.consumers

import android.app.Application
import androidx.databinding.ObservableBoolean
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetConsumersUseCase
import com.srproject.presentation.BaseViewModel

class ConsumersViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    private val getConsumersUseCase = GetConsumersUseCase(repository)
    val navigateToConsumerDetailsCommand = SingleLiveEvent<String>()
    val adapter = ConsumersAdapter {
        navigateToConsumerDetailsCommand.postValue(it)
    }
    val hasItems = ObservableBoolean()

    fun start() {
        getConsumersUseCase.findConsumers {
            adapter.items = it
            hasItems.set(it.isNotEmpty())
        }
    }

}