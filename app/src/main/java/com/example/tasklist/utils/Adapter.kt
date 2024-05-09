package com.example.tasklist.utils

import com.example.tasklist.model.Task
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R

class Adapter(val tasks: List<Task>, private val context: Context): RecyclerView.Adapter<Adapter.ViewHolder>() {


    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {


        fun bind(task: Task) {
            val name = itemView.findViewById<TextView>(R.id.task_name)
            val checked = itemView.findViewById<CheckBox>(R.id.task_checkbox)

            name?.text = task.name
            checked?.isChecked = task.checked
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.task_item, parent, false)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        holder.bind(task)
    }


}