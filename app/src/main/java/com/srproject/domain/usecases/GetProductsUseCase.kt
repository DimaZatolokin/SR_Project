package com.srproject.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.srproject.data.Repository
import com.srproject.data.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetProductsUseCase(private val repository: Repository) : BaseUseCase() {

    private lateinit var productsLiveData: LiveData<List<Product>>
    private val productsObserver = Observer<List<Product>> {
        action?.invoke(it)
    }
    private var action: ((List<Product>) -> Unit)? = null

    fun obtainProducts(action: (List<Product>) -> Unit) {
        this.action = action
        launch {
            productsLiveData = repository.getProducts()
            withContext(Dispatchers.Main) { productsLiveData.observeForever(action) }
        }
    }

    override fun cancel() {
        super.cancel()
        if (::productsLiveData.isInitialized) {
            productsLiveData.removeObserver(productsObserver)
        }
    }
}