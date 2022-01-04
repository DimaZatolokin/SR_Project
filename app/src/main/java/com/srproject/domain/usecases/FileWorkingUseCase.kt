package com.srproject.domain.usecases

import android.content.Context
import android.net.Uri
import com.srproject.data.Repository
import com.srproject.data.models.OrderPosition
import com.srproject.data.models.Product
import com.srproject.domain.mappers.OrderPositionsPresentationMapper
import com.srproject.domain.mappers.OrderPresentationMapper
import com.srproject.presentation.models.OrderUI
import kotlinx.coroutines.launch

class FileWorkingUseCase(private val repository: Repository) : BaseUseCase() {

    private val orderPresentationMapper = OrderPresentationMapper()
    private val positionsPresentationMapper = OrderPositionsPresentationMapper()

    fun writeAllDataToFile(context: Context, ordersUI: List<OrderUI>, producrs: List<Product>) {
        val orders = ordersUI.map { orderPresentationMapper.fromPresentation(it) }
        val positions = arrayListOf<OrderPosition>()
        ordersUI.forEach { orderUI ->
            positions.addAll(orderUI.positions.map { positionsPresentationMapper.fromPresentation(it) })
        }
        launch {
            repository.writeDataToFile(context, orders, positions, producrs)
        }
    }

    fun restoreDataFromFile(context: Context, uri: Uri, onDataReceived: (String?) -> Unit) {
        launch {
            val data = repository.readDataFromFile(context, uri)
            onDataReceived.invoke(data)
        }
    }
}