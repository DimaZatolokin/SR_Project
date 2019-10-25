package com.srproject.presentation.productEdit

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.data.models.Product
import com.srproject.domain.usecases.DeleteProductUseCase
import com.srproject.domain.usecases.GetProductByIdUseCase
import com.srproject.domain.usecases.UpdateProductUseCase
import com.srproject.presentation.BaseViewModel

class ProductEditViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    val name = ObservableField<String>()
    val price = ObservableField<String>()
    val showErrorCommand = SingleLiveEvent<Errors>()
    val navigateBackCommand = SingleLiveEvent<Unit>()
    private val updateProductUseCase = UpdateProductUseCase(viewModelScope, repository)
    private val getProductUseCase = GetProductByIdUseCase(viewModelScope, repository)
    private val deleteProductUseCase = DeleteProductUseCase(viewModelScope, repository)
    private var id = 0L

    fun start(id: Long) {
        this.id = id
        getProductUseCase.getProductById(id) {
            it?.run {
                this@ProductEditViewModel.name.set(name)
                this@ProductEditViewModel.price.set(price.toString())
            } ?: showErrorCommand.postValue(Errors.PRODUCT_NOT_FOUND)
        }
    }

    fun onSaveClicked() {
        if (validateFields()) {
            updateProductUseCase.updateProduct(Product(id, name.get()!!, price.get()!!.toInt()))
            navigateBackCommand.call()
        }
    }

    fun onDeleteClicked() {
        deleteProductUseCase.deleteProduct(id)
        navigateBackCommand.call()
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

enum class Errors { NAME, PRICE, PRODUCT_NOT_FOUND }
