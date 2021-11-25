package com.example.mvvm_sample.mvvm.uidata.home

import com.example.mvvm_sample.mvvm.uidata.CommentListItemData

sealed class HomeViewState {
    object Initial: HomeViewState()
    data class Indicator(val needShow: Boolean): HomeViewState()
    data class CommentsLoaded(val itemDataList: List<CommentListItemData>): HomeViewState()
    object ShowAddCommentDialog: HomeViewState()
}