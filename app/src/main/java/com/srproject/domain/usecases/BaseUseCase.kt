package com.srproject.domain.usecases

import kotlinx.coroutines.CoroutineScope

abstract class BaseUseCase(protected val coroutineScope: CoroutineScope) {

    open fun onClear() {}
}