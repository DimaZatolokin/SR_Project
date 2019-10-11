package com.srproject.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.srproject.data.Repository

abstract class BaseViewModel(application: Application, protected val repository: Repository) :
    AndroidViewModel(application) {


}