package com.srproject.presentation.productCreate

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.data.models.Product
import com.srproject.domain.usecases.CreateProductUseCase
import com.srproject.presentation.BaseViewModel

class ProductCreateViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    val name = ObservableField<String>()
    val price = ObservableField<String>()
    private val useCase = CreateProductUseCase(viewModelScope, repository)
    val showErrorCommand = SingleLiveEvent<Errors>()
    val navigateBackCommand = SingleLiveEvent<Unit>()

    fun createProduct() {
        if (validateFields()) {
            useCase.createProduct(Product(-1, name.get()!!, price.get()!!.toInt()))
            navigateBackCommand.call()
        }
    }

    private fun validateFields(): Boolean {
        if (name.get().isNullOrBlank()) {
            showErrorCommand.postValue(Errors.NAME)
            return false
        }
        if (price.get().isNullOrBlank()) {
            showErrorCommand.postValue(Errors.PRICE)
            return false
        }
        return true
    }
}

enum class Errors { NAME, PRICE }
