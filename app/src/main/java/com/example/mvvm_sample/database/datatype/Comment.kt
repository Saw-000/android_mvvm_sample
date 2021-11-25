package com.example.mvvm_sample.database.datatype

import java.util.*

data class Comment(
    val text: String?,
    val creatorId: UUID,
    val createdDate: String,
    val id: UUID = UUID.randomUUID()
)