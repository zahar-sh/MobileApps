package com.example.store.app

import androidx.room.Room

import android.app.Application
import com.example.store.dao.AppDatabase
import com.example.store.dao.AppDatabaseHistory

class App : Application() {
    var database: AppDatabase? = null
        private set

    var databaseHistory: AppDatabaseHistory? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build();

        databaseHistory =
            Room.databaseBuilder(this, AppDatabaseHistory::class.java, "databaseHistory")
                .fallbackToDestructiveMigration()
                .build();
    }

    companion object {
        var instance: App? = null
    }
}