package com.srproject.common

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.srproject.data.Repository
import com.srproject.presentation.BaseViewModel
import java.lang.reflect.InvocationTargetException

class ViewModelFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    private var repository: Repository? = null

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (BaseViewModel::class.java.isAssignableFrom(modelClass)) {
            try {
                repository = RepositoryFactory.provideRepository()
                return modelClass.getConstructor(Application::class.java, Repository::class.java)
                    .newInstance(application, repository)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }

        }
        return super.create(modelClass)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (instance == null) {
                        instance = ViewModelFactory(application)
                    }
                }
            }
            return instance
        }

        @VisibleForTesting
        fun destroyInstance() {
            instance = null
        }
    }


}