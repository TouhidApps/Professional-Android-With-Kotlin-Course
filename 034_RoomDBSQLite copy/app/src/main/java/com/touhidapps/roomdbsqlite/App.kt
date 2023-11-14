package com.touhidapps.roomdbsqlite

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {

    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getDb(applicationContext)

    }

}