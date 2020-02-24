package com.srproject.presentation.settings

import android.app.Application
import androidx.databinding.ObservableBoolean
import com.srproject.common.SingleLiveEvent
import com.srproject.data.Repository
import com.srproject.data.ResultObject
import com.srproject.domain.usecases.SyncDataUseCase
import com.srproject.presentation.BaseViewModel

class SettingsViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    private val syncUseCase = SyncDataUseCase(repository)
    val progress = ObservableBoolean()
    val success = ObservableBoolean()
    val showErrorCommand = SingleLiveEvent<Unit>()

    fun start() {

    }

    fun onSyncClicked() {
        success.set(false)
        progress.set(true)
        syncUseCase.sendLocalDataToServer {
            progress.set(false)
            when (it) {
                is ResultObject.Success -> success.set(true)
                is ResultObject.Error -> showErrorCommand.call()
            }
        }
    }
}