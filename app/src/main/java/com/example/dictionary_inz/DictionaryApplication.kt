package com.example.dictionary_inz

import android.app.Application
import com.example.dictionary_inz.data.AppDataContainer

class DictionaryApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppDataContainer.initializeDatabase(this)
    }
}