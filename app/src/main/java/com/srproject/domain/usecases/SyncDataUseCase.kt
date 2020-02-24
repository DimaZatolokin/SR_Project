package com.srproject.domain.usecases

import com.srproject.data.EmptyError
import com.srproject.data.Repository
import com.srproject.data.ResultObject
import kotlinx.coroutines.launch

class SyncDataUseCase(private val repository: Repository) : BaseUseCase() {

    fun sendLocalDataToServer(onResult: (ResultObject<Unit>) -> Unit) {
        launch {
            val resultOrders = repository.sendOrdersToServer()
            val resultOrderPositions = repository.sendOrdersPositionsToServer()
            val resultProducts = repository.sendProductsToServer()
            onResult.invoke( переделать
                if (resultOrders is ResultObject.Success
                    && resultOrderPositions is ResultObject.Success
                    && resultProducts is ResultObject.Success
                ) ResultObject.Success(Unit)
                else ResultObject.Error(EmptyError())
            )
        }
    }
}