package com.example.store.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.store.entity.HistoryItem

@Dao
interface HistoryItemDao {
    @Query("SELECT * FROM HistoryItems")
    fun getAll(): List<HistoryItem?>?

    @Query("SELECT * FROM HistoryItems WHERE id = :id")
    fun getById(id: Int): HistoryItem?

    @Insert(onConflict = REPLACE)
    fun insert(employee: HistoryItem?)

    @Update
    fun update(employee: HistoryItem?)

    @Delete
    fun delete(employee: HistoryItem?)
}