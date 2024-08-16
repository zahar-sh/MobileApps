package com.example.lab1.service

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.lab1.model.LogStatistic

class DatabaseManager(var context: Context) {
    val databaseHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = databaseHelper.writableDatabase
    }

    fun insert(log: LogStatistic) {
        var cv = ContentValues().apply {
            put(COL_TIME, log.time)
            put(COL_SATIETY, log.satiety)
        }
        var result = db?.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    @SuppressWarnings("Range")
    fun read(): ArrayList<String> {
        val dataList = ArrayList<String>()
        val cursor = db?.query(TABLE_NAME, null,null,null,null,null,null)
        while (cursor?.moveToNext()!!) {
            val time = cursor.getString(cursor.getColumnIndex(COL_TIME))
            val satiety = cursor.getInt(cursor.getColumnIndex(COL_SATIETY))
            dataList.add("Time: $time  Satiety: $satiety\n")
        }
        cursor.close()
        return dataList
    }

    fun closeDb() {
        databaseHelper.close()
    }
}