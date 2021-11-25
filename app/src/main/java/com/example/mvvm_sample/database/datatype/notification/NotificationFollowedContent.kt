package com.example.mvvm_sample.database.datatype.notification

import java.util.*

data class NotificationFollowedContent(
    val followedUserId: UUID,
    val followingUserId: UUID,
    val date: String
)