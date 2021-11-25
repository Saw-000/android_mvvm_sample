package com.example.mvvm_sample.database.api

import com.example.mvvm_sample.database.datatype.Comment
import com.example.mvvm_sample.database.datatype.CommentList
import com.example.mvvm_sample.database.datatype.User
import com.example.mvvm_sample.database.datatype.notification.Notification
import com.example.mvvm_sample.database.datatype.notification.NotificationContents
import com.example.mvvm_sample.database.datatype.notification.NotificationFollowedContent
import com.example.mvvm_sample.enum.NotificationType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*

class Api(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    // ユーザ取得
    suspend fun getUser(id: UUID): User?
        = DebugDatabase.getUser(id)

    // ユーザが作成したコメント取得
    suspend fun getCommentsCreatedBy(userId: UUID): List<Comment>?
        = DebugDatabase.getCommentsCreatedBy(userId)

    // リストに紐づくコメントの取得
    suspend fun getCommentsIn(listId: UUID): List<Comment>?
        = DebugDatabase.getCommentsIn(listId)

    // 有効範囲内のコメントを取得
    suspend fun getTimelineComments(): List<Comment>?
        = DebugDatabase.getTimelineComments()

    // ユーザが作成したコメントリスト取得
    fun getCommentListCreatedBy(userid: UUID): List<CommentList>?
        = DebugDatabase.getCommentListCreatedBy(userid)

    // ユーザへの通知取得
    fun getNotifications(userid: UUID): List<Notification>?
        = DebugDatabase.getNotifications(userid)

    // コメントリスト作成
    fun createCommentList(list: CommentList): Boolean
        = false// debug: 実装


    // コメント作成
    fun createComment(comment: Comment): Boolean
        = false// debug: 実装
}


// デバッグ用のdatabase
object DebugDatabase {
    private val userIds = listOf<UUID>(UUID.randomUUID(), UUID.randomUUID())
    private val commentIds = listOf<UUID>(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())
    private val commentListIds = listOf<UUID>(UUID.randomUUID(), UUID.randomUUID())

    // ユーザ
    private val users: List<User> = listOf(
        User(
            name = "me",
            listIds = listOf(
                commentListIds[0],
                commentListIds[1]
            ),
            id = userIds[0]
        ),
        User(
            name = "friend1",
            listIds = listOf(
                commentListIds[0]
            ),
            id = userIds[1]
        )
    )

    // コメント
    private val comments: List<Comment> = listOf(
        Comment(
            text = "キーマカレー",
            creatorId = userIds[0],
            createdDate = "2021/8/10",
            id = commentIds[0]
        ),
        Comment(
            text = "グリーンカレー",
            creatorId = userIds[0],
            createdDate = "2021/8/20",
            id = commentIds[1]
        ),
        Comment(
            text = "洋梨",
            creatorId = userIds[1],
            createdDate = "2021/11/11",
            id = commentIds[2]
        ),
        Comment(
            text = "森",
            creatorId = userIds[1],
            createdDate = "2021/11/11",
            id = commentIds[3]
        ),
        Comment(
            text = "カレーぱん",
            creatorId = userIds[1],
            createdDate = "2021/11/11",
            id = commentIds[4]
        ),
    )

    // コメントリスト
    private val commentLists: List<CommentList> = listOf(
        CommentList(
            title = "リスト: カレー",
            creatorId = userIds[0],
            commentIds = listOf(
                commentIds[0],
                commentIds[1],
                commentIds[4]
            ),
            id = commentListIds[0]
        ),
        CommentList(
            title = "リスト: 緑",
            creatorId = userIds[0],
            commentIds = listOf(
                commentIds[1],
                commentIds[2],
                commentIds[3]
            ),
            id = commentListIds[1]
        )
    )

    // 通知
    private val notifications: Map<UUID, List<Notification>> = mapOf(
        userIds[0] to listOf(
            Notification(
                type = NotificationType.FOLLOWED,
                contents = NotificationContents(
                    followedContent = NotificationFollowedContent(
                        followedUserId = userIds[0],
                        followingUserId = userIds[1],
                        date = "2021/11/05"
                    ),
                    listFollowedContent = null,
                    repliedContent = null
                )
            ),
            Notification(
                type = NotificationType.FOLLOWED,
                contents = NotificationContents(
                    followedContent = NotificationFollowedContent(
                        followedUserId = userIds[0],
                        followingUserId = userIds[1],
                        date = "2021/11/04"
                    ),
                    listFollowedContent = null,
                    repliedContent = null
                )
            ),
        ),
        userIds[1] to listOf(

        )
    )

    // ログインユーザ
    val loginUserId: UUID
        get() = userIds[0]

    // ユーザ取得
    fun getUser(id: UUID) : User? {
        for (user in users) {
            if (user.id == id) {
                return user
            }
        }
        return null
    }

    // ユーザが作成したコメント取得
    fun getCommentsCreatedBy(userId: UUID) : List<Comment>
        = comments.filter { it.creatorId == userId }


    // リストに紐づくコメントの取得
    fun getCommentsIn(listId: UUID) : List<Comment>? {
        for (list in commentLists) {
            if (list.id == listId) {
                val listComments = mutableListOf<Comment>()
                for (comment in comments) {
                    if (list.commentIds.contains(comment.id)) {
                        listComments.add(comment)
                    }
                }
                return listComments
            }
        }
        return null
    }

    // ユーザが作成したコメントリストの取得
    fun getCommentListCreatedBy(userid: UUID) : List<CommentList>
        = commentLists.filter { it.creatorId == userid }


    // 有効範囲内のコメント手おtく
    fun getTimelineComments() : List<Comment> {
        val comments = mutableListOf<Comment>()
        for (i in 0..100) {
            comments.add(
                Comment(
                    text = "$i 個目のコメント\nこんにちは。",
                    creatorId = loginUserId,
                    createdDate = Date().toString()
                )
            )
        }
        return comments
    }

    // ユーザへの通知取得
    fun getNotifications(userid: UUID) : List<Notification>?
        = notifications[userid]

}
