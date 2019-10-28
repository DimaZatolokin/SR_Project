package com.srproject.presentation.products

import android.app.Application
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.domain.usecases.GetProductsUseCase
import com.srproject.presentation.BaseViewModel

class ProductsViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository), ProductEditClickListener {

    val adapter = ProductsAdapter(this)
    private val getProductsUseCase = GetProductsUseCase(repository)
    val navigateToProductEditCommand = SingleLiveEvent<Long>()

    fun start() {
        getProductsUseCase.obtainProducts {
            adapter.items = it
        }
    }

    override fun onEditClicked(id: Long) {
        navigateToProductEditCommand.postValue(id)
    }

    override fun onCleared() {
        super.onCleared()
        getProductsUseCase.cancel()
    }
}