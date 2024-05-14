package com.example.tasklist.model

import java.util.UUID

data class Task (val name: String, var checked: Boolean, val id: UUID)