package com.example.mvvm_sample.database.datatype.notification

import com.example.mvvm_sample.enum.NotificationType
import java.util.*

data class Notification(
    val type: NotificationType,
    val contents: NotificationContents,
    val id: UUID = UUID.randomUUID(),
)