package com.jeremieguillot.database.presentation.main.recyclier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jeremieguillot.database.R
import com.jeremieguillot.database.domain.model.ShoppingItem

interface ShoppingAdapterListener {
    fun onShoppingItemClicked(item: ShoppingItem)
}

class ShoppingAdapter(private val listener: ShoppingAdapterListener) :
    RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    private var data: List<ShoppingItem> = ArrayList()

    fun setData(data: List<ShoppingItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvQuantity: TextView = view.findViewById(R.id.tv_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tvName.text = item.name
        holder.tvQuantity.text = item.quantity.toString()
        holder.itemView.setOnClickListener { listener.onShoppingItemClicked(item) }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}