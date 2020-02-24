package com.srproject.common

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.srproject.data.datasource.local.db.AppDataBase
import com.srproject.data.datasource.local.prefs.PreferencesDataSource
import com.srproject.data.Repository
import com.srproject.data.datasource.network.NetworkDataSource

class RepositoryFactory {

    companion object {

        fun provideRepository() = Repository.getInstance()

        fun initRepository(context: Context) {
            val preferences =
                PreferencesDataSource(
                    context
                )
            val dataBase = AppDataBase.getInstance(context)
            val networkDataSource = NetworkDataSource(FirebaseFirestore.getInstance())
            Repository.init(dataBase, preferences, networkDataSource)
        }
    }
}