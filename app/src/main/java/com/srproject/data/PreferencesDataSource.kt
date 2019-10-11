package com.srproject.data

import android.content.Context

private const val PREF_FILE_NAME = "sr_pref"

class PreferencesDataSource(context: Context) {

    private val prefs = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
}