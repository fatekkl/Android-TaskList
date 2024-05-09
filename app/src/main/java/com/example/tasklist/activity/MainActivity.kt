package com.example.tasklist.activity

import com.example.tasklist.model.Task
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.utils.Adapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class MainActivity : AppCompatActivity() {


    // CRIAR TASKS NO BANCO DE DADOS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configRecyclerView()
        configFab()
    }


    override fun onResume() {
        super.onResume()
        configRecyclerView()
    }


    fun parseJson(input: String): List<Task> {
        if (input.isEmpty()) {
            return emptyList()
        }

        val gson = Gson()

        val type = object : TypeToken<List<Task>>() {}.type

        return gson.fromJson(input, type)
    }

    private fun getData(): String {
        val tasksFile = File(filesDir, "tasks.json")

        if (tasksFile.exists()) {

            return tasksFile.readText()
        }
        return "File dont exist"
    }

    private fun configRecyclerView() {
        val adapter = Adapter(context = this, tasks = parseJson(getData()) )
        val recyclerView = findViewById<RecyclerView>(R.id.activity_recyclerView)
        recyclerView.adapter = adapter
    }

    private fun configFab() {
        val fab = findViewById<FloatingActionButton>(R.id.activity_fab)

        fab.setOnClickListener {
            val intent = Intent(this, TaskForm::class.java)

            startActivity(intent)
        }
    }

}


