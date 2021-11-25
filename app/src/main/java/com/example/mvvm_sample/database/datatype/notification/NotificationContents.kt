package com.example.mvvm_sample.database.datatype.notification

data class NotificationContents(
    val followedContent: NotificationFollowedContent?,
    val listFollowedContent: NotificationListFollowedContent?,
    val repliedContent: NotificationRepliedContent?
)