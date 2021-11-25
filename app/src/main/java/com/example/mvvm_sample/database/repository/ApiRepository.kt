package com.example.mvvm_sample.database.repository

import com.example.mvvm_sample.database.api.Api
import com.example.mvvm_sample.database.api.DebugDatabase
import com.example.mvvm_sample.database.datatype.Comment
import com.example.mvvm_sample.database.datatype.CommentList
import com.example.mvvm_sample.database.datatype.User
import com.example.mvvm_sample.database.datatype.notification.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class ApiRepository(
    private val api: Api = Api()
) {
    companion object {
        private val _loginUserId = MutableStateFlow<UUID?>(DebugDatabase.loginUserId)
        val loginUserId: StateFlow<UUID?> = _loginUserId
    }

    // ユーザ取得
    suspend fun getUser(): User? {
        val id = loginUserId.value ?: return null
        return api.getUser(id)
    }

    // ユーザが作成したコメント取得
    suspend fun getComments(): List<Comment>? {
        val id = loginUserId.value ?: return null
        return api.getCommentsCreatedBy(id)
    }

    // リスト内のコメント取得
    suspend fun getCommentsIn(listId: UUID): List<Comment>?
        = api.getCommentsIn(listId)

    suspend fun getTimelineComments(): List<Comment>?
        = api.getTimelineComments()

    // ユーザが作成したコメントリスト取得
    suspend fun getCommentList(): List<CommentList>? {
        val id = loginUserId.value ?: return null
        return api.getCommentListCreatedBy(id)
    }

    // ユーザへの通知取得
    suspend fun getNotifications(): List<Notification>? {
        val id = loginUserId.value ?: return null
        return api.getNotifications(id)
    }

    // コメントリスト作成
    suspend fun createCommentList(list: CommentList): Boolean
        = api.createCommentList(list)

    // コメントリスト作成
    suspend fun createComment(comment: Comment): Boolean
        = api.createComment(comment)
}