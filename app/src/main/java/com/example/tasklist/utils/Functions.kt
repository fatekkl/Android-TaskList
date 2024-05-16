package com.example.tasklist.utils

import android.content.Context
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.example.tasklist.R
import com.example.tasklist.model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.UUID

fun parseFromJson(input: String): List<Task> {
    if (input.isEmpty()) {
        return emptyList()
    }

    val gson = Gson()

    val type = object : TypeToken<List<Task>>() {}.type

    return gson.fromJson(input, type)
}

fun addTask(task: Task, context: Context) {
    val tasks = mutableListOf<Task>()
    val gson = Gson()

    val tasksFile = File(context.filesDir, "tasks.json")

    if (tasksFile.exists() && tasksFile.length() > 0) { // Verifica se o arquivo existe e não está vazio

        val jsonTasks = tasksFile.readText()  // captura tasks antigas

        tasks.addAll(parseFromJson(jsonTasks)) // adiciona task antigas na lista local
    }

    tasks.add(task) // adiciona task nova na lista local

    val jsonTasks = gson.toJson(tasks)

    try {

        FileWriter(tasksFile).use { writer -> // escreve no arquivo dados atualizados
            writer.write(jsonTasks)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun updateTasks(tasks: List<Task>, context: Context) {

    val gson = Gson()

    val jsonTasks = gson.toJson(tasks)

    try {

        val tasksFile = File(context.filesDir, "tasks.json")

        FileWriter(tasksFile).use { writer -> // escreve no arquivo dados atualizados
            writer.write(jsonTasks)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

}

fun deleteTask(tasks: MutableList<Task>, index: Int, context: Context) {


    try {

        if (index != -1) {
            tasks.removeAt(index)
            updateTasks(tasks, context)
        }


    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "deu ruim viado", Toast.LENGTH_SHORT).show()
    }

}

fun updateJSONCheckbox(tasks: List<Task>, context: Context, id: UUID) {

    try {
        val index = tasks.indexOfFirst { it.id == id }

        tasks[index].checked = !tasks[index].checked


        updateTasks(tasks, context)
    } catch (e: IOException) {
        e.printStackTrace()
    }


}

fun setCheckbox(checkBox: CheckBox, id: UUID, context: Context) {

    try {
        val tasksFile = File(context.filesDir, "tasks.json")
        val tasks = parseFromJson(tasksFile.readText())

        val task = getTask(tasks, id)

        if (task != null) checkBox.isChecked = task.checked



    } catch (e: IOException) {
        e.printStackTrace()
    }


}

fun getTask(tasks: List<Task>, id: UUID): Task? {

    return tasks.find { it.id == id }
}

