package com.srproject.common

import android.content.Context
import com.srproject.data.AppDataBase
import com.srproject.data.FileDataSource
import com.srproject.data.PreferencesDataSource
import com.srproject.data.Repository

class RepositoryFactory {

    companion object {

        fun provideRepository() = Repository.getInstance()

        fun initRepository(context: Context) {
            val preferences = PreferencesDataSource(context)
            val dataBase = AppDataBase.getInstance(context)
            val fileDataSource = FileDataSource()
            Repository.init(dataBase, preferences, fileDataSource)
        }
    }
}