package com.example.tasklist.utils

import android.content.Context
import android.util.Log
import android.widget.CheckBox
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

fun deleteTask(newTasks: String?, taskFiles: File) {

    try {
        FileWriter(taskFiles).use { writer ->
            writer.write(newTasks)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}


fun updateJSONCheckbox(tasks: List<Task>, context: Context, id: UUID) {
   // implementar atualização no JSON primeiro, depois fazer com que updateUICheckbox puxe do JSON, atualmente ele só está salvando na memoria local
}

fun updateUICheckbox(checkBox: CheckBox, tasks: List<Task>, id: UUID) {

    val task = getTask(tasks, id)

    checkBox.isChecked = task.checked
    Log.i("tasks_updateUI", "chamou")
}

fun getTask(tasks: List<Task>, id: UUID): Task {
    val index = tasks.indexOfFirst { it.id == id }

    return tasks[index]
}
