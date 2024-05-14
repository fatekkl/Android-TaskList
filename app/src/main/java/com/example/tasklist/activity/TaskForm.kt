package com.example.tasklist.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasklist.R
import com.example.tasklist.model.Task
import com.example.tasklist.utils.parseFromJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.UUID

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




    // adiciona funcionalidade ao botão de Salvar
    private fun configSaveButton() {
        val btn = findViewById<Button>(R.id.submit_button)
        val nameField = findViewById<EditText>(R.id.submit_taskName)

        btn.setOnClickListener {
            val name = nameField.text.toString()
            val checked = false

            val task = Task(name, checked, UUID.randomUUID())


            saveData(task)
            finish()
        }
    }



    //
    private fun saveData(task: Task) {
        val tasks = mutableListOf<Task>()
        val gson = Gson()

        val tasksFile = File(filesDir, "tasks.json")

        if (tasksFile.exists() && tasksFile.length() > 0) { // Verifica se o arquivo existe e não está vazio

            val jsonTasks = tasksFile.readText()  // captura dados antigos

            tasks.addAll(parseFromJson(jsonTasks)) // adiciona dados antigos a lista local
        }

        tasks.add(task) // adiciona novos dados a lista local

        val jsonTasks = gson.toJson(tasks) // transforma a lista local em uma string JSON

        // escreve dados atualizados no arquivo JSON
        try {
            FileWriter(tasksFile).use { writer ->
                writer.write(jsonTasks)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }






}