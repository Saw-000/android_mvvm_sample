package com.example.mvvm_sample.mvvm.model

import com.example.mvvm_sample.database.datatype.Comment
import com.example.mvvm_sample.database.datatype.User
import com.example.mvvm_sample.database.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow

const val TAG_LOG_SO = "debug_so"

class HomeModel(
    private val apiRepo: ApiRepository = ApiRepository()
) {

    // コメント列
    private val _comments = MutableStateFlow<List<Comment>?>(null)
    val comments = _comments

    suspend fun updateComments() {
        _comments.value = apiRepo.getTimelineComments()
    }

    suspend fun getUser(): User?
        = apiRepo.getUser()

    suspend fun createComment(comment: Comment): Boolean
       = apiRepo.createComment(comment)

}