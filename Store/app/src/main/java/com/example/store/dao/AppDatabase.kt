package com.example.store.dao

import androidx.room.RoomDatabase

import androidx.room.Database
import com.example.store.entity.Item

@Database(entities = [Item::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao?
}