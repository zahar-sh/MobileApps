package com.example.store.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.store.entity.Item

class ItemCompareCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return (oldItem.name == newItem.name)
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}