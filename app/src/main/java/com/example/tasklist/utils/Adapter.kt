package com.example.tasklist.utils

import com.example.tasklist.model.Task
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.IOException

class Adapter(val tasks: MutableList<Task>, private val context: Context) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {


    class ViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {


        fun bind(task: Task, context: Context) {
            val name = itemView.findViewById<TextView>(R.id.task_name)
            val checked = itemView.findViewById<CheckBox>(R.id.task_checkbox)
            val deleteButton = itemView.findViewById<Button>(R.id.task_button)

            name?.text = task.name
            checked?.isChecked = task.checked

            deleteButton.setOnClickListener {

                val id = task.id

                val taskFiles = File(context.filesDir, "tasks.json")
                val gson = Gson()

                val tasksJson = taskFiles.readText()

                val tasks: MutableList<Task> =
                    gson.fromJson(tasksJson, object : TypeToken<MutableList<Task>>() {}.type)


                val index = tasks.indexOfFirst { it.id == id }




                if (index != -1) {
                    tasks.removeAt(index)

                }

                val newTasks = gson.toJson(tasks)


                updateTask(newTasks, taskFiles)
            }


        }

        private fun updateTask(newTasks: String?, taskFiles: File) {

            try {
                FileWriter(taskFiles).use { writer ->
                    writer.write(newTasks)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.task_item, parent, false)


        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]

        holder.bind(task, context)
    }


}