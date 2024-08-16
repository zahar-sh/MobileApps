package com.example.store.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "HistoryItems",
    indices = [Index(value = ["data"], unique = true)]
)
class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "data")
    val data: String,
    val name: String
)