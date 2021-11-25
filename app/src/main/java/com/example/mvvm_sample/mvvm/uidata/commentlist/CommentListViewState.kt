package com.example.mvvm_sample.mvvm.uidata.commentlist

import com.example.mvvm_sample.database.datatype.CommentList
import com.example.mvvm_sample.mvvm.uidata.CommentListItemData

sealed class CommentListViewState {
    object Initial: CommentListViewState()
    object ShowAddCommentDialog: CommentListViewState()
    data class UpdatedCommentLists(val lists: List<String>): CommentListViewState()
    data class UpdatedCurrentList(val list: CommentList?): CommentListViewState()
    data class UpdatedCurrentListComments(val data: List<CommentListItemData>): CommentListViewState()
}