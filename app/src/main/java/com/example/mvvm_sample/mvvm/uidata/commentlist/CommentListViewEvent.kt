package com.example.mvvm_sample.mvvm.uidata.commentlist

import java.util.*

sealed class CommentListViewEvent {
    data class ClickItem(val id: UUID): CommentListViewEvent()
    data class ReplyItem(val id: UUID): CommentListViewEvent()
    data class ListItem(val id: UUID): CommentListViewEvent()
    data class ClickListSpinnerItem(val index: Int?): CommentListViewEvent()
    object AddCommentListButton: CommentListViewEvent()
    data class CallbackAddCommentListDialog(val data: AddCommentListDialogData?): CommentListViewEvent()
}