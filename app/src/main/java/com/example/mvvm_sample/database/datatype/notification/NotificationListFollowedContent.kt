package com.example.mvvm_sample.database.datatype.notification

import java.util.*

data class NotificationListFollowedContent(
    val followingUserId: UUID,
    val followedListId: UUID,
    val date: String
    // その他必要なパラメータ
)