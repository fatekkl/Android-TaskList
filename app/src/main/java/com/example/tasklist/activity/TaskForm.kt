package com.example.tasklist.activity

import com.example.tasklist.model.Task
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasklist.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.IOException

class TaskForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configSaveButton()
    }

    private fun configSaveButton() {
        val btn = findViewById<Button>(R.id.submit_button)
        val name_field = findViewById<EditText>(R.id.submit_taskName)

        btn.setOnClickListener {
            val name = name_field.text.toString()
            val checked = false

            val task = Task(name, checked)


            saveData(task)
            finish()
        }
    }


    private fun saveData(task: Task) {
        val tasks = mutableListOf<Task>()
        val gson = Gson()

        val tasksFile = File(filesDir, "tasks.json")

        if (tasksFile.exists() && tasksFile.length() > 0) { // Verifica se o arquivo existe e não está vazio
            val jsonTasks = tasksFile.readText()
            tasks.addAll(gson.fromJson(jsonTasks, object : TypeToken<List<Task>>() {}.type))
        }

        tasks.add(task)

        val jsonTasks = gson.toJson(tasks)

        try {
            FileWriter(tasksFile).use { writer ->
                writer.write(jsonTasks)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Log.i("tasks_info", jsonTasks)
    }




}