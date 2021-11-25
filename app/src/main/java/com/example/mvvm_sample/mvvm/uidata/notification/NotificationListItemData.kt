package com.example.mvvm_sample.mvvm.uidata.notification

import com.example.mvvm_sample.database.datatype.notification.Notification
import com.example.mvvm_sample.enum.NotificationType
import java.util.*

data class NotificationListItemData(
    val id: UUID,
    val dateStr: String,
    val text: String
) {
    companion object {
        fun from(notification: Notification): NotificationListItemData
            = when (notification.type) {
                NotificationType.FOLLOWED -> {
                    val data = notification.contents.followedContent
                    NotificationListItemData(
                        id = notification.id,
                        dateStr = "[${data?.date}]",
                        text = "${data?.followingUserId}さんからフォローされました！"
                    )
                }
                NotificationType.LIST_FOLLOWED -> {
                    val data = notification.contents.listFollowedContent
                    NotificationListItemData(
                        id = notification.id,
                        dateStr = "[${data?.date}]",
                        text = "${data?.followingUserId}さんからあなたが作成したリスト[${data?.followedListId}]がフォローされました！"
                    )
                }
                NotificationType.REPLIED -> {
                    val data = notification.contents.repliedContent
                    NotificationListItemData(
                        id = notification.id,
                        dateStr = "[${data?.date}]",
                        text = "${data?.replierId}さんからあなたのコメントかリプライされました！"
                    )
                }
            }

    }
}