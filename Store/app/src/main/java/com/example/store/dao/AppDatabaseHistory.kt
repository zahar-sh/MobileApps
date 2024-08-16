package com.example.store.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.store.entity.HistoryItem

@Database(entities = [HistoryItem::class], version = 4)
abstract class AppDatabaseHistory : RoomDatabase() {
    abstract fun historyItemDao(): HistoryItemDao?
}