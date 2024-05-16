package com.example.tasklist.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.model.Task

class Adapter(val tasks: List<Task>, private val context: Context) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(task: Task) {
            val name = itemView.findViewById<TextView>(R.id.task_name)
            val checkBox = itemView.findViewById<CheckBox>(R.id.task_checkbox)
            val deleteButton = itemView.findViewById<Button>(R.id.task_button)
            val container = itemView.findViewById<ConstraintLayout>(R.id.task_container)
            val id = task.id

            name?.text = task.name

            //arrumar atualização da interface quando deletado
            deleteButton.setOnClickListener { it ->
                val index = tasks.indexOfFirst { it.id == id }

                deleteTask(tasks.toMutableList(), index, context)

                // gambiarra fudida pra dar refresh e manter tasks atualizadas
//                (context as Activity).finish()
//
//                val intent = Intent(context, MainActivity::class.java)
//
//                context.startActivity(intent)
            }


            checkBox.setOnClickListener {
                updateJSONCheckbox(tasks, context, id) // salva no JSON se está marcado ou não
            }

            setCheckbox(checkBox, id, context)
            if (checkBox.isChecked) {
                container.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))

            }

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