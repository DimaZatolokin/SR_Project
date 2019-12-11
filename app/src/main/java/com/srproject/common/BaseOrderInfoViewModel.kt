package com.srproject.common

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import com.srproject.data.Repository
import com.srproject.presentation.BaseViewModel

abstract class BaseOrderInfoViewModel(application: Application, repository: Repository) :
    BaseViewModel(application, repository) {

    val consumer = ObservableField<String>()
    val dueDate = ObservableField<String>()
    val dateCreated = ObservableField<String>()
    val comment = ObservableField<String>()
    val realPrice = ObservableField<String>()
    val calculatedPrice = ObservableField<String>()
    val done = ObservableBoolean()
    val active = ObservableBoolean()
    val paid = ObservableBoolean()
    val number = ObservableLong()
}