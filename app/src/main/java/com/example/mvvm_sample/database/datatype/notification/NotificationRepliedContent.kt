package com.example.mvvm_sample.database.datatype.notification

import java.util.*

data class NotificationRepliedContent(
    val replierId: UUID,
    val repliedCommentId: UUID,
    val replyingCommentId: UUID,
    val date: String
    // その他必要なパラmーた
)