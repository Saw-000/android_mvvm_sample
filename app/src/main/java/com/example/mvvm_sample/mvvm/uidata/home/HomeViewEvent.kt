package com.example.mvvm_sample.mvvm.uidata.home

import java.util.*

sealed class HomeViewEvent {
    data class ClickItem(val id: UUID): HomeViewEvent()
    data class ReplyItem(val id: UUID): HomeViewEvent()
    data class ListItem(val id: UUID): HomeViewEvent()
    object UpdateDataButton: HomeViewEvent()
    object NewCommentButton: HomeViewEvent()
    data class CallbackAddCommentDialog(val data: AddCommentDialogCallbackData?): HomeViewEvent()
}