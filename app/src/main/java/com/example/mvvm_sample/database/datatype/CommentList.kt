package com.example.mvvm_sample.database.datatype

import java.util.*

data class CommentList(
    val title: String,
    val creatorId: UUID,
    val commentIds: List<UUID>,
    val id: UUID = UUID.randomUUID()
)