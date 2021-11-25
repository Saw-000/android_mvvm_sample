package com.example.mvvm_sample.mvvm.model

import com.example.mvvm_sample.database.datatype.Comment
import com.example.mvvm_sample.database.datatype.CommentList
import com.example.mvvm_sample.database.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class CommentListModel(
    private val apiRepo: ApiRepository = ApiRepository()
) {
    private val _commentLists = MutableStateFlow<List<CommentList>?>(null)
    val commentLists: StateFlow<List<CommentList>?> = _commentLists

    private val _currentListComments = MutableStateFlow<List<Comment>?>(null)
    val currentListComments: StateFlow<List<Comment>?> = _currentListComments

    suspend fun updateCommentLists() {
        _commentLists.value = apiRepo.getCommentList()
    }

    suspend fun updateCurrentListComments(_listId: UUID?) {
        val id = _listId ?: run {
            _currentListComments.value = null
            return@updateCurrentListComments
        }
        _currentListComments.value = apiRepo.getCommentsIn(id)
    }

    fun getUserId(): UUID?
        = ApiRepository.loginUserId.value

    suspend fun createCommentList(newList: CommentList): Boolean
        = apiRepo.createCommentList(newList)


}