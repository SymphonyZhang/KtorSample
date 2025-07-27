package com.francis.ktorsample.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.francis.ktorsample.app.App

object AppDataStore {
    // 创建DataStore
    private val App.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_data")

    // 对外开放的DataStore变量
    val dataStore = App.instance.dataStore
}