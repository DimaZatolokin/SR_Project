package com.srproject.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.srproject.presentation.BaseViewModel

private const val OBTAIN_VIEWMODEL_EXCEPTION_MESSAGE =
    "Activity is null when trying to obtain viewModel"

fun <T : BaseViewModel> Fragment.obtainViewModel(clazz: Class<T>): T {
    activity?.run {
        return ViewModelProviders.of(this, ViewModelFactory.getInstance(this.application))
            .get(clazz)
    } ?: throw IllegalStateException(OBTAIN_VIEWMODEL_EXCEPTION_MESSAGE)
}