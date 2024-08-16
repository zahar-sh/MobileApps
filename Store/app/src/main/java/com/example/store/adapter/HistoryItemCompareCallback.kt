package com.example.store.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.store.entity.HistoryItem

class HistoryItemCompareCallback : DiffUtil.ItemCallback<HistoryItem>() {
    override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
        return (oldItem.name == newItem.name)
    }

    override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
        return oldItem.data == newItem.data
    }
}