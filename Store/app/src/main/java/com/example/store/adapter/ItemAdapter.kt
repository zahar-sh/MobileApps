package com.example.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.store.R
import com.example.store.entity.Item
import com.example.store.fragment.ToMainFragment
import com.squareup.picasso.Picasso

class ItemAdapter(var context: Context) : ListAdapter<Item,
        ItemAdapter.ViewHolder>(ItemCompareCallback()) {

    private var allList: List<Item> = listOf()
    private var listener: ToMainFragment? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(getItem(position).img).into(holder.imgView);
        holder.name.text = getItem(position).name
        holder.firm.text = getItem(position).producer_name
        holder.price.text = getItem(position).price.toString()
        holder.itemView.setOnClickListener {
            listener?.onClick(getItem(position))
        }
    }

    fun setListener(toFragmentNews: ToMainFragment) {
        listener = toFragmentNews
    }

    fun setData(data: List<Item>) {
        submitList(null)
        submitList(data)
        notifyDataSetChanged()
        allList = data
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgView: ImageView = itemView!!.findViewById(R.id.imageView)
        var name: TextView = itemView!!.findViewById(R.id.tvName)
        var firm: TextView = itemView!!.findViewById(R.id.tvFirm)
        var price: TextView = itemView!!.findViewById(R.id.tvPrice)
    }


}
