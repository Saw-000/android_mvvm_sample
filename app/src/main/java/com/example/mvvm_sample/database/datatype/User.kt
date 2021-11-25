package com.example.mvvm_sample.database.datatype

import java.util.*

data class User(
    val name: String,
    var listIds: List<UUID>,
    var id: UUID = UUID.randomUUID()
)