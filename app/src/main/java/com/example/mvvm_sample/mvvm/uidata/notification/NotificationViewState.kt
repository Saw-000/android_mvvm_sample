package com.example.mvvm_sample.mvvm.uidata.notification

sealed class NotificationViewState {
    object Initial: NotificationViewState()
    data class UpdateList(val notifications: List<NotificationListItemData>): NotificationViewState()
}