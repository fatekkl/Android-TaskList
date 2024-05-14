package com.example.tasklist.utils

import com.example.tasklist.model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun parseFromJson(input: String): List<Task> {
    if (input.isEmpty()) {
        return emptyList()
    }

    val gson = Gson()

    val type = object : TypeToken<List<Task>>() {}.type

    return gson.fromJson(input, type)
}
