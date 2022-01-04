package com.srproject.presentation.settings

import android.app.Application
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.data.models.FileDataModel
import com.srproject.domain.usecases.*
import com.srproject.presentation.BaseViewModel

class SettingsViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    private val historyUseCase = GetHistoryUseCase(repository)
    private val getProductsUseCase = GetProductsUseCase(repository)
    private val fileUseCase = FileWorkingUseCase(repository)
    private val createProductsUseCase = CreateProductUseCase(repository)
    private val createOrderUseCase = CreateOrderUseCase(repository)
    val showFileLoadError = SingleLiveEvent<Unit>()
    val showDataNotEmptyError = SingleLiveEvent<Unit>()

    fun onSaveDataToFileClicked() {
        historyUseCase.obtainAllOrders { orders ->
            getProductsUseCase.obtainProducts { producrs ->
                fileUseCase.writeAllDataToFile(getApplication(), orders, producrs)
            }
        }
    }

    fun onRestoreDataFromFileClicked(uri: Uri) {
        historyUseCase.obtainAllOrders { orders ->
            if (orders.isNotEmpty()) {
                showDataNotEmptyError.postValue(Unit)
            } else {
                getProductsUseCase.obtainProducts { producrs ->
                    if (producrs.isNotEmpty()) {
                        showDataNotEmptyError.postValue(Unit)
                    } else {
                        fileUseCase.restoreDataFromFile(getApplication(), uri) {
                            if (it == null) {
                                showFileLoadError.postValue(Unit)
                            } else {
                                parseDataJson(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun parseDataJson(data: String) {
        val gson = Gson()
        try {
            val fileDataModel = gson.fromJson<FileDataModel>(data, FileDataModel::class.java)
            fileDataModel.products.forEach {
                createProductsUseCase.createProduct(it)
            }
            createOrderUseCase.addOrders(fileDataModel.orders, fileDataModel.positions)
        } catch (e: JsonSyntaxException) {
            showFileLoadError.postValue(Unit)
        }
    }
}