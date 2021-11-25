package com.example.mvvm_sample.mvvm.uidata.notification

import java.util.*

sealed class NotificationViewEvent {
    data class ClickListItem(val notificationId: UUID): NotificationViewEvent()
}