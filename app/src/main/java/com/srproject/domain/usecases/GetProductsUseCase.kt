package com.srproject.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.srproject.data.Repository
import com.srproject.data.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetProductsUseCase(coroutineScope: CoroutineScope, private val repository: Repository) :
    BaseUseCase(coroutineScope) {

    private lateinit var productsLiveData: LiveData<List<Product>>
    private val productsObserver = Observer<List<Product>> {
        action?.invoke(it)
    }
    private var action: ((List<Product>) -> Unit)? = null

    fun obtainProducts(action: (List<Product>) -> Unit) {
        this.action = action
        coroutineScope.launch {
            productsLiveData = repository.getProducts()
            productsLiveData.observeForever(action)
        }
    }

    override fun onClear() {
        super.onClear()
        if (::productsLiveData.isInitialized) {
            productsLiveData.removeObserver(productsObserver)
        }
    }
}