package com.example.tasklist.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tasklist.R
import com.example.tasklist.model.Task
import com.example.tasklist.utils.addTask
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




    // adiciona funcionalidade ao botão de salvar
    private fun configSaveButton() {
        val btn = findViewById<Button>(R.id.submit_button)
        val nameField = findViewById<EditText>(R.id.submit_taskName)

        btn.setOnClickListener {
            val name = nameField.text.toString()
            val checked = false

            val task = Task(name, checked, UUID.randomUUID())


            addTask(task, this) // salva dados no arquivo
            finish()
        }
    }







}