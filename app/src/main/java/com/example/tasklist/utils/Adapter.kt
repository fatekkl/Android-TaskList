package com.example.tasklist.utils

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
import com.example.tasklist.model.Task
import com.google.gson.Gson
import java.io.File

class Adapter(val tasks: List<Task>, private val context: Context) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(task: Task) {
            val name = itemView.findViewById<TextView>(R.id.task_name)
            val checkBox = itemView.findViewById<CheckBox>(R.id.task_checkbox)
            val deleteButton = itemView.findViewById<Button>(R.id.task_button)
            val id = task.id

            val gson = Gson()

            name?.text = task.name



            //arrumar atualização da interface quando deletado
            deleteButton.setOnClickListener { it ->

                val taskFiles = File(it.context.filesDir, "tasks.json")


                val tasksJson = taskFiles.readText()

                val tasks: MutableList<Task> = parseFromJson(tasksJson).toMutableList()

                val index = tasks.indexOfFirst { it.id == id }

                if (index != -1) {
                    tasks.removeAt(index)  // remove task que foi selecionada

                    val newTasks = gson.toJson(tasks) // transforma em json


                    deleteTask(newTasks, taskFiles) // atualiza tasks
                }
            }


            checkBox.setOnClickListener {
                updateJSONCheckbox(tasks, context, id) // salva no JSON se está marcado ou não
            }
            // como opção posso chamar um metódo para atualizar a UI
            updateUICheckbox(checkBox, id, context)
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