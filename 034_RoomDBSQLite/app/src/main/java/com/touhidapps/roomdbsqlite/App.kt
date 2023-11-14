package com.touhidapps.roomdbsqlite

import android.app.Application
import com.touhidapps.roomdbsqlite.db.AppDatabase

class App: Application() {

    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getDb(applicationContext)

    }

}