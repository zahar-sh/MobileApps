package com.example.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.store.R
import com.example.store.entity.HistoryItem

class HistoryItemAdapter : ListAdapter<HistoryItem,
        HistoryItemAdapter.ViewHolder>(HistoryItemCompareCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.historyitem_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = getItem(position).name
        holder.data.text = getItem(position).data
    }

    fun setData(data: List<HistoryItem>) {
        submitList(null)
        submitList(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var data: TextView = itemView.findViewById(R.id.textView8)
        var name: TextView = itemView.findViewById(R.id.textView7)
    }
}