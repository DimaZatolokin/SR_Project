package com.srproject.common

import android.app.Application

class SRApp : Application() {

    override fun onCreate() {
        super.onCreate()
        RepositoryFactory.initRepository(this)
    }
}