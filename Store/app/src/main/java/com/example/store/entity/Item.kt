package com.example.store.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Items",
    indices = [Index(value = ["id", "name"], unique = true)]
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    val href: String,
    @ColumnInfo(name = "name")
    val name: String,
    val producer_name: String,
    val price: Double,
    val type: String,
    val img: String,
    val about: String
)
