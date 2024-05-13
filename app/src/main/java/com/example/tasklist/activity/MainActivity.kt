package com.example.tasklist.activity

import com.example.tasklist.model.Task
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.utils.Adapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class MainActivity : AppCompatActivity() {


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


    // transforma a string JSON em uma List com o items do tipo Task
    private fun parseJson(input: String): List<Task> {
        if (input.isEmpty()) {
            return emptyList()
        }

        val gson = Gson()

        val type = object : TypeToken<List<Task>>() {}.type

        return gson.fromJson(input, type)
    }


    // captura dados do arquivo .JSON
    private fun getData(): String {
        val tasksFile = File(filesDir, "tasks.json")

        if (tasksFile.exists()) {

            return tasksFile.readText()
        }
        return "[]"
    }



    // configura recycler view  e o adapter para puxar do arquivo JSON
    private fun configRecyclerView() {
        val adapter = Adapter(context = this, tasks = parseJson(getData()) )
        val recyclerView = findViewById<RecyclerView>(R.id.activity_recyclerView)
        recyclerView.adapter = adapter
    }




    //adiciona funciolidade ao arquivo JSON
    private fun configFab() {
        val fab = findViewById<FloatingActionButton>(R.id.activity_fab)

        fab.setOnClickListener {
            val intent = Intent(this, TaskForm::class.java)

            startActivity(intent)
        }
    }

}


