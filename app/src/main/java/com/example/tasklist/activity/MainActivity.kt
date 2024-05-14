package com.example.tasklist.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.model.Task
import com.example.tasklist.utils.Adapter
import com.example.tasklist.utils.parseFromJson
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
        val tasks = parseFromJson(getData())
        val adapter = Adapter(context = this, tasks = tasks )
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


