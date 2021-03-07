package com.jeremieguillot.database.presentation.main.recyclier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jeremieguillot.database.R
import com.jeremieguillot.database.domain.model.CourseItem
import kotlin.collections.ArrayList

interface CourseAdapterListener {
    fun onCourseClicked(course: CourseItem)
}

class CourseAdapter(private val listener: CourseAdapterListener) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    private var data: List<CourseItem> = ArrayList()

    fun setData(data: List<CourseItem>) {
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
        val course = data[position]
        holder.tvName.text = course.name
        holder.tvQuantity.text = course.quantity.toString()
        holder.itemView.setOnClickListener { listener.onCourseClicked(course) }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}